package com.xjbg.query.parser.es;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.xjbg.query.parser.antlr4.cql.CQLSearchLexer;
import com.xjbg.query.parser.antlr4.cql.CQLSearchParser;
import com.xjbg.query.parser.enums.LanguageType;
import com.xjbg.query.parser.enums.ParserType;
import com.xjbg.query.parser.utils.StringUtil;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author kesc
 * @since 2024-01-29 16:51
 */
public class Es8Cql2DslQueryParser extends AbstractEsQueryParser {
    private static final Logger log = LoggerFactory.getLogger(Es8Cql2DslQueryParser.class);

    @Override
    public Query parse(String expression) {
        //如果表达式为空或者*，则返回全部数据
        if (StringUtil.isBlank(expression) || StringUtil.STAR.equals(expression.trim())) {
            return Query.of(q -> q.matchAll(m -> m));
        }
        CharStream stream = CharStreams.fromString(expression);
        CQLSearchLexer lexer = new CQLSearchLexer(stream);
        CQLSearchParser searchParser = new CQLSearchParser(new CommonTokenStream(lexer));

        Query query = parseExpressionContext(searchParser.cql().expression());
        log.debug("cql:{},dsl:{}", expression, query);
        return query;
    }

    private Query parseExpressionContext(CQLSearchParser.ExpressionContext expressionContext) {
        if (expressionContext instanceof CQLSearchParser.LrExprContext) {
            return parseLrExprContext((CQLSearchParser.LrExprContext) expressionContext);
        } else if (expressionContext instanceof CQLSearchParser.BoolExprContext) {
            return parseBoolExprContext((CQLSearchParser.BoolExprContext) expressionContext);
        } else if (expressionContext instanceof CQLSearchParser.EqExprContext) {
            return parseEqExprContext((CQLSearchParser.EqExprContext) expressionContext);
        } else if (expressionContext instanceof CQLSearchParser.NotExprContext) {
            return parseNotExprContext((CQLSearchParser.NotExprContext) expressionContext);
        } else if (expressionContext instanceof CQLSearchParser.IdentityExprContext) {
            return parseIdentityContext((CQLSearchParser.IdentityExprContext) expressionContext);
        } else {
            throw new IllegalArgumentException(String.format("unsupported expression[%s]!", expressionContext.getText()));
        }
    }

    private Query parseLrExprContext(CQLSearchParser.LrExprContext lrExprContext) {
        CQLSearchParser.ExpressionContext expression = lrExprContext.expression();
        return parseExpressionContext(expression);
    }

    private Query parseNotExprContext(CQLSearchParser.NotExprContext notExprContext) {
        CQLSearchParser.ExpressionContext expression = notExprContext.expression();
        return Query.of(q -> q.bool(b -> b.mustNot(parseExpressionContext(expression))));
    }

    private Query parseBoolExprContext(CQLSearchParser.BoolExprContext boolExprContext) {
        CQLSearchParser.ExpressionContext leftExpr = boolExprContext.expression(0);
        CQLSearchParser.ExpressionContext rightExpr = boolExprContext.expression(1);

        Query leftQuery = parseExpressionContext(leftExpr);
        Query rightQuery = parseExpressionContext(rightExpr);
        if (isNot(boolExprContext)) {
            return Query.of(q -> q.bool(b -> b.must(leftQuery).mustNot(rightQuery)));
        } else if (isOr(boolExprContext)) {
            return Query.of(q -> q.bool(b -> b.should(leftQuery).should(rightQuery)));
        } else if (isAnd(boolExprContext)) {
            return Query.of(q -> q.bool(b -> b.must(leftQuery).must(rightQuery)));
        } else {
            throw new IllegalArgumentException(String.format("unsupported logic operator[%s]!", boolExprContext.operator.getText()));
        }
    }

    private Query parseEqExprContext(CQLSearchParser.EqExprContext eqExprContext) {
        String field, op = eqExprContext.operator.getText();
        Object value;
        //左半边的字段值
        if (eqExprContext.leftExpr instanceof CQLSearchParser.IdentityExprContext) {
            field = eqExprContext.leftExpr.getText();
        } else {
            throw new IllegalArgumentException(String.format("unsupported expression[%s] for leftExpr!", eqExprContext.leftExpr.getText()));
        }
        //右半边的关键词
        if (eqExprContext.rightExpr == null || StringUtil.isBlank(eqExprContext.rightExpr.getText())) {
            //右边的表达式为空说明只有关键词 没有指定字段 目前处理方式是搜索默认的字段
            value = field;
            field = getGlobalFieldName();
        } else if (eqExprContext.rightExpr instanceof CQLSearchParser.IdentityExprContext) {
            value = eqExprContext.rightExpr.getText();
        } else if (eqExprContext.rightExpr instanceof CQLSearchParser.LrExprContext && ((CQLSearchParser.LrExprContext) eqExprContext.rightExpr).expression() instanceof CQLSearchParser.OrItemExprContext) {
            value = parseOrItemExprContext((CQLSearchParser.OrItemExprContext) ((CQLSearchParser.LrExprContext) eqExprContext.rightExpr).expression());
        } else if (eqExprContext.rightExpr instanceof CQLSearchParser.OrItemExprContext) {
            value = parseOrItemExprContext((CQLSearchParser.OrItemExprContext) eqExprContext.rightExpr);
        } else {
            throw new IllegalArgumentException(String.format("unsupported expression[%s] for rightExpr!", eqExprContext.rightExpr.getText()));
        }
        if (StringUtil.isBlank(op)) {
            op = StringUtil.COLON;
        }
        return parseFieldValue(field, op.trim(), value);
    }

    @SuppressWarnings({"unchecked"})
    private Query parseFieldValue(String field, String op, Object value) {
        String fieldKeyword = field + ".keyword";
        String valueStr = StringUtil.valueOf(value);
        boolean isStar = StringUtil.STAR.equals(StringUtil.trim(valueStr));
        switch (op) {
            case "!=":
                if (isStar) {
                    return Query.of(q -> q.bool(b -> b.mustNot(mn -> mn.exists(e -> e.field(field)))));
                } else {
                    if (value instanceof List) {
                        List<String> stringValues = (List<String>) value;
                        List<FieldValue> fieldValues = stringValues.stream().map(FieldValue::of).collect(Collectors.toList());
                        return Query.of(q -> q.bool(b -> b.mustNot(mn -> mn.terms(t -> t.field(fieldKeyword).terms(trms -> trms.value(fieldValues))))));
                    }
                    return Query.of(q -> q.bool(b -> b.mustNot(mn -> mn.term(t -> t.field(fieldKeyword).value(FieldValue.of(valueStr))))));
                }
            case "=":
                if (isStar) {
                    return Query.of(q -> q.exists(e -> e.field(field)));
                } else {
                    if (value instanceof List) {
                        List<String> stringValues = (List<String>) value;
                        List<FieldValue> fieldValues = stringValues.stream().map(FieldValue::of).collect(Collectors.toList());
                        return Query.of(q -> q.terms(t -> t.field(fieldKeyword).terms(trms -> trms.value(fieldValues))));
                    }
                    return Query.of(q -> q.term(t -> t.field(fieldKeyword).value(FieldValue.of(valueStr))));
                }
            case ">=":
                return Query.of(q -> q.range(r -> r.field(field).gte(JsonData.of(value))));
            case ">":
                return Query.of(q -> q.range(r -> r.field(field).gt(JsonData.of(value))));
            case "<=":
                return Query.of(q -> q.range(r -> r.field(field).lte(JsonData.of(value))));
            case "<":
                return Query.of(q -> q.range(r -> r.field(field).lt(JsonData.of(value))));
            default:
                if (isStar) {
                    return Query.of(q -> q.exists(e -> e.field(field)));
                } else {
                    Function<String, Query> queryBuilderFunction = s -> {
                        String orValue = StringUtil.valueOf(s);
                        if (orValue.contains(StringUtil.STAR)) {
                            return Query.of(q -> q.wildcard(w -> w.field(field).value(orValue)));
                        } else if (StringUtil.isNotBlank(orValue) && orValue.length() > 2 && ((orValue.startsWith("\"") && orValue.endsWith("\"")) || (orValue.startsWith("'") && orValue.endsWith("'")))) {
                            return Query.of(q -> q.matchPhrase(mp -> mp.field(field).query(orValue.substring(1, orValue.length() - 1))));
                        } else if (StringUtil.isBlank(orValue)) {
                            return Query.of(q -> q.match(m -> m.field(field).query("")));
                        } else {
                            return Query.of(q -> q.match(m -> m.field(field).query(orValue)));
                        }
                    };
                    if (value instanceof List) {
                        List<Query> shouldQueries = ((List<String>) value).stream()
                                .map(x -> queryBuilderFunction.apply(StringUtil.valueOf(x)))
                                .collect(Collectors.toList());
                        return Query.of(q -> q.bool(b -> b.should(shouldQueries)));
                    } else {
                        return queryBuilderFunction.apply(valueStr);
                    }
                }
        }
    }

    private Query parseIdentityContext(CQLSearchParser.IdentityExprContext identityExprContext) {
        return parseFieldValue(getGlobalFieldName(), StringUtil.COLON, identityExprContext.getText());
    }

    @Override
    public LanguageType language() {
        return LanguageType.CQL;
    }

    @Override
    public ParserType parserType() {
        return ParserType.ES8;
    }

}