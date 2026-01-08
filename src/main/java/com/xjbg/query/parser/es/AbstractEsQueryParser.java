package com.xjbg.query.parser.es;

import com.xjbg.query.parser.antlr4.cql.CQLSearchParser;
import com.xjbg.query.parser.base.AbstractQueryParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kesc
 * @since 2024-01-29 16:54
 */
public abstract class AbstractEsQueryParser extends AbstractQueryParser<Object> {

    protected List<String> parseOrItemExprContext(CQLSearchParser.OrItemExprContext orItemExprContext) {
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

    protected boolean isAnd(CQLSearchParser.BoolExprContext boolExprContext) {
        return boolExprContext.BOOLAND() != null || boolExprContext.AND() != null;
    }

    protected boolean isOr(CQLSearchParser.BoolExprContext boolExprContext) {
        return boolExprContext.BOOLOR() != null || boolExprContext.OR() != null;
    }

    protected boolean isNot(CQLSearchParser.BoolExprContext boolExprContext) {
        return boolExprContext.NOT() != null;
    }


}
