package com.xjbg.query.parser.es;

import com.xjbg.query.parser.base.AbstractQueryParser;
import com.xjbg.query.parser.enums.ParserType;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author kesc
 * @since 2024-01-29 16:54
 */
public abstract class AbstractEsQueryParser extends AbstractQueryParser<QueryBuilder> {

    @Override
    public ParserType parserType() {
        return ParserType.ES;
    }

}
