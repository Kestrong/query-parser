package com.xjbg.query.parser.base;

import com.xjbg.query.parser.enums.LanguageType;
import com.xjbg.query.parser.enums.ParserType;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kesc
 * @since 2024-01-29 10:32
 */
@Getter
@Setter
public abstract class AbstractQueryParser<T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private String globalFieldName = "message";

    public abstract T parse(String expression);

    public abstract LanguageType language();

    public abstract ParserType parserType();

}
