package com.xjbg.query.parser.base;

import com.xjbg.query.parser.enums.LanguageType;
import com.xjbg.query.parser.enums.ParserType;

/**
 * @author kesc
 * @since 2024-02-02 10:25
 */
public class NoopSqlQueryParser extends AbstractQueryParser<String> {

    @Override
    public String parse(String expression) {
        return expression;
    }

    @Override
    public LanguageType language() {
        return LanguageType.SQL;
    }

    @Override
    public ParserType parserType() {
        return ParserType.NOOP;
    }

}
