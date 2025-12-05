package com.xjbg.query.parser.jdbc;

import com.xjbg.query.parser.antlr4.cql.CQLSearchLexer;
import com.xjbg.query.parser.antlr4.cql.CQLSearchParser;
import com.xjbg.query.parser.enums.LanguageType;
import com.xjbg.query.parser.utils.StringUtil;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcCql2SqlQueryParser extends AbstractJdbcQueryParser {

    @Override
    public String parse(String expression) {
        //如果表达式为空或者*，则返回全部数据
        if (StringUtil.isBlank(expression) || StringUtil.STAR.equals(expression.trim())) {
            return " 1 = 1 ";
        }
        CharStream stream = CharStreams.fromString(expression);
        CQLSearchLexer lexer = new CQLSearchLexer(stream);
        CQLSearchParser searchParser = new CQLSearchParser(new CommonTokenStream(lexer));

        String sql = parseExpressionContext(searchParser.cql().expression());
        log.debug("cql:{}, sql:{}", expression, sql);
        return sql;
    }

    private String parseExpressionContext(CQLSearchParser.ExpressionContext expressionContext) {
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

    private String parseLrExprContext(CQLSearchParser.LrExprContext lrExprContext) {
        CQLSearchParser.ExpressionContext expression = lrExprContext.expression();
        return "(" + parseExpressionContext(expression) + ")";
    }

    private String parseNotExprContext(CQLSearchParser.NotExprContext notExprContext) {
        CQLSearchParser.ExpressionContext expression = notExprContext.expression();
        return "NOT (" + parseExpressionContext(expression) + ")";
    }

    private String parseBoolExprContext(CQLSearchParser.BoolExprContext boolExprContext) {
        CQLSearchParser.ExpressionContext leftExpr = boolExprContext.expression(0);
        CQLSearchParser.ExpressionContext rightExpr = boolExprContext.expression(1);

        String leftQuery = parseExpressionContext(leftExpr);
        String rightQuery = parseExpressionContext(rightExpr);

        if (isNot(boolExprContext)) {
            return leftQuery + " AND NOT " + rightQuery;
        } else if (isOr(boolExprContext)) {
            return leftQuery + " OR " + rightQuery;
        } else if (isAnd(boolExprContext)) {
            return leftQuery + " AND " + rightQuery;
        } else {
            throw new IllegalArgumentException(String.format("unsupported logic operator[%s]!", boolExprContext.operator.getText()));
        }
    }

    private String parseEqExprContext(CQLSearchParser.EqExprContext eqExprContext) {
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

    private String parseFieldValue(String field, String op, Object value) {
        String valueStr = StringUtil.valueOf(value);
        boolean isStar = StringUtil.STAR.equals(StringUtil.trim(valueStr));
        
        // 判断是否为数值类型
        boolean isNumeric = false;
        if (value instanceof Number || (valueStr != null && isNumeric(valueStr))) {
            isNumeric = true;
        }
        
        switch (op) {
            case "!=":
                if (isStar) {
                    return field + " IS NULL";
                } else {
                    if (value instanceof List) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(field).append(" NOT IN (");
                        List<?> values = (List<?>) value;
                        for (int i = 0; i < values.size(); i++) {
                            if (i > 0) sb.append(", ");
                            if (isNumericValue(values.get(i))) {
                                sb.append(values.get(i));
                            } else {
                                sb.append("'").append(values.get(i)).append("'");
                            }
                        }
                        sb.append(")");
                        return sb.toString();
                    }
                    return field + " != " + (isNumeric ? valueStr : "'" + valueStr + "'");
                }
            case "=":
                if (isStar) {
                    return field + " IS NOT NULL";
                } else {
                    if (value instanceof List) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(field).append(" IN (");
                        List<?> values = (List<?>) value;
                        for (int i = 0; i < values.size(); i++) {
                            if (i > 0) sb.append(", ");
                            if (isNumericValue(values.get(i))) {
                                sb.append(values.get(i));
                            } else {
                                sb.append("'").append(values.get(i)).append("'");
                            }
                        }
                        sb.append(")");
                        return sb.toString();
                    }
                    return field + " = " + (isNumeric ? valueStr : "'" + valueStr + "'");
                }
            case ">=":
                return field + " >= " + (isNumeric ? value : "'" + value + "'");
            case ">":
                return field + " > " + (isNumeric ? value : "'" + value + "'");
            case "<=":
                return field + " <= " + (isNumeric ? value : "'" + value + "'");
            case "<":
                return field + " < " + (isNumeric ? value : "'" + value + "'");
            default:
                if (isStar) {
                    return field + " IS NOT NULL";
                } else {
                    if (value instanceof List) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("(");
                        List<?> values = (List<?>) value;
                        for (int i = 0; i < values.size(); i++) {
                            if (i > 0) sb.append(" OR ");
                            if (isNumericValue(values.get(i))) {
                                sb.append(field).append(" LIKE '%").append(values.get(i)).append("%'");
                            } else {
                                sb.append(field).append(" LIKE '%").append(values.get(i)).append("%'");
                            }
                        }
                        sb.append(")");
                        return sb.toString();
                    } else {
                        if (valueStr.contains(StringUtil.STAR)) {
                            return field + " LIKE '" + valueStr.replace("*", "%") + "'";
                        } else if (StringUtil.isNotBlank(valueStr) && valueStr.length() > 2 && 
                                ((valueStr.startsWith("\"") && valueStr.endsWith("\"")) || 
                                 (valueStr.startsWith("'") && valueStr.endsWith("'")))) {
                            return field + " = " + (isNumeric ? valueStr.substring(1, valueStr.length() - 1) : "'" + valueStr.substring(1, valueStr.length() - 1) + "'");
                        } else if (StringUtil.isBlank(valueStr)) {
                            return field + " = ''";
                        } else {
                            return field + " LIKE '%" + valueStr + "%'";
                        }
                    }
                }
        }
    }
    
    /**
     * 判断字符串是否为数值类型
     */
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 判断值是否为数值类型
     */
    private boolean isNumericValue(Object value) {
        if (value instanceof Number) {
            return true;
        }
        if (value instanceof String) {
            return isNumeric((String) value);
        }
        return false;
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

    private String parseIdentityContext(CQLSearchParser.IdentityExprContext identityExprContext) {
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