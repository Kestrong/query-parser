package com.xjbg.query.parser.es;

import com.xjbg.query.parser.antlr4.cql.CQLSearchLexer;
import com.xjbg.query.parser.antlr4.cql.CQLSearchParser;
import com.xjbg.query.parser.enums.LanguageType;
import com.xjbg.query.parser.utils.StringUtil;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author kesc
 * @since 2024-01-29 16:51
 */
public class EsCql2DslQueryParser extends AbstractEsQueryParser {

    @Override
    public QueryBuilder parse(String expression) {
        //如果表达式为空或者*，则返回全部数据
        if (StringUtil.isBlank(expression) || StringUtil.STAR.equals(expression.trim())) {
            return QueryBuilders.matchAllQuery();
        }
        CharStream stream = CharStreams.fromString(expression);
        CQLSearchLexer lexer = new CQLSearchLexer(stream);
        CQLSearchParser searchParser = new CQLSearchParser(new CommonTokenStream(lexer));

        QueryBuilder queryBuilder = parseExpressionContext(searchParser.cql().expression());
        log.debug("cql:{},dsl:{}", expression, queryBuilder);
        return queryBuilder;
    }

    private QueryBuilder parseExpressionContext(CQLSearchParser.ExpressionContext expressionContext) {
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

    private QueryBuilder parseLrExprContext(CQLSearchParser.LrExprContext lrExprContext) {
        CQLSearchParser.ExpressionContext expression = lrExprContext.expression();
        return parseExpressionContext(expression);
    }

    private QueryBuilder parseNotExprContext(CQLSearchParser.NotExprContext notExprContext) {
        CQLSearchParser.ExpressionContext expression = notExprContext.expression();
        return QueryBuilders.boolQuery().mustNot(parseExpressionContext(expression));
    }

    private BoolQueryBuilder parseBoolExprContext(CQLSearchParser.BoolExprContext boolExprContext) {
        CQLSearchParser.ExpressionContext leftExpr = boolExprContext.expression(0);
        CQLSearchParser.ExpressionContext rightExpr = boolExprContext.expression(1);

        QueryBuilder leftQuery = parseExpressionContext(leftExpr);
        QueryBuilder rightQuery = parseExpressionContext(rightExpr);
        if (isNot(boolExprContext)) {
            return QueryBuilders.boolQuery().must(leftQuery).mustNot(rightQuery);
        } else if (isOr(boolExprContext)) {
            return QueryBuilders.boolQuery().should(leftQuery).should(rightQuery);
        } else if (isAnd(boolExprContext)) {
            return QueryBuilders.boolQuery().must(leftQuery).must(rightQuery);
        } else {
            throw new IllegalArgumentException(String.format("unsupported logic operator[%s]!", boolExprContext.operator.getText()));
        }
    }

    private QueryBuilder parseEqExprContext(CQLSearchParser.EqExprContext eqExprContext) {
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    private QueryBuilder parseFieldValue(String field, String op, Object value) {
        String fieldKeyword = field + ".keyword";
        String valueStr = StringUtil.valueOf(value);
        boolean isStar = StringUtil.STAR.equals(StringUtil.trim(valueStr));
        switch (op) {
            case "!=":
                if (isStar) {
                    return QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(field));
                } else {
                    if (value instanceof List) {
                        return QueryBuilders.boolQuery().mustNot(QueryBuilders.termsQuery(fieldKeyword, (List) value));
                    }
                    return QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(fieldKeyword, valueStr));
                }
            case "=":
                if (isStar) {
                    return QueryBuilders.existsQuery(field);
                } else {
                    if (value instanceof List) {
                        return QueryBuilders.termsQuery(fieldKeyword, (List) value);
                    }
                    return QueryBuilders.termQuery(fieldKeyword, valueStr);
                }
            case ">=":
                return QueryBuilders.rangeQuery(field).gte(value);
            case ">":
                return QueryBuilders.rangeQuery(field).gt(value);
            case "<=":
                return QueryBuilders.rangeQuery(field).lte(value);
            case "<":
                return QueryBuilders.rangeQuery(field).lt(value);
            default:
                if (isStar) {
                    return QueryBuilders.existsQuery(field);
                } else {
                    Function<String, QueryBuilder> queryBuilderFunction = s -> {
                        String orValue = StringUtil.valueOf(s);
                        if (orValue.contains(StringUtil.STAR)) {
                            return QueryBuilders.wildcardQuery(field, orValue);
                        } else if (StringUtil.isNotBlank(orValue) && orValue.length() > 2 && ((orValue.startsWith("\"") && orValue.endsWith("\"")) || (orValue.startsWith("'") && orValue.endsWith("'")))) {
                            return QueryBuilders.matchPhraseQuery(field, orValue.substring(1, orValue.length() - 1));
                        } else if (StringUtil.isBlank(orValue)) {
                            return QueryBuilders.matchQuery(field, StringUtil.EMPTY);
                        } else {
                            return QueryBuilders.matchQuery(field, orValue);
                        }
                    };
                    if (value instanceof List) {
                        BoolQueryBuilder orQuery = QueryBuilders.boolQuery();
                        ((List) value).forEach(x -> {
                            orQuery.should(queryBuilderFunction.apply(StringUtil.valueOf(x)));
                        });
                        return orQuery;
                    } else {
                        return queryBuilderFunction.apply(valueStr);
                    }
                }
        }
    }

    private List<String> parseOrItemExprContext(CQLSearchParser.OrItemExprContext orItemExprContext) {
        List<String> items = new ArrayList<>();
        items.add(orItemExprContext.item.getText());
        if (orItemExprContext.orItems() != null && !orItemExprContext.orItems().isEmpty()) {
            orItemExprContext.orItems().forEach(x -> items.add(x.item.getText()));
        }
        if (items.size() > 1 && items.contains("*")) {
            throw new IllegalArgumentException(String.format("'*' can not mix with other value like [%s].", Arrays.toString(items.toArray())));
        }
        return items;
    }

    private QueryBuilder parseIdentityContext(CQLSearchParser.IdentityExprContext identityExprContext) {
        return parseFieldValue(getGlobalFieldName(), StringUtil.COLON, identityExprContext.getText());
    }

    private boolean isAnd(CQLSearchParser.BoolExprContext boolExprContext) {
        return boolExprContext.BOOLAND() != null || boolExprContext.AND() != null;
    }

    private boolean isOr(CQLSearchParser.BoolExprContext boolExprContext) {
        return boolExprContext.BOOLOR() != null || boolExprContext.OR() != null;
    }

    private boolean isNot(CQLSearchParser.BoolExprContext boolExprContext) {
        return boolExprContext.NOT() != null;
    }

    @Override
    public LanguageType language() {
        return LanguageType.CQL;
    }

}
