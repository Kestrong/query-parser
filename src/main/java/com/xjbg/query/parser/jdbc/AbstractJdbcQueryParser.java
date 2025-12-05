package com.xjbg.query.parser.jdbc;

import com.xjbg.query.parser.base.AbstractQueryParser;
import com.xjbg.query.parser.enums.ParserType;

/**
 * @author kesc
 * @since 2024-01-29 16:54
 */
public abstract class AbstractJdbcQueryParser extends AbstractQueryParser<String> {

    @Override
    public ParserType parserType() {
        return ParserType.JDBC;
    }

}
