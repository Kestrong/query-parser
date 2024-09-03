package com.xjbg.query.parser.base;

import com.xjbg.query.parser.enums.LanguageType;
import com.xjbg.query.parser.enums.ParserType;
import com.xjbg.query.parser.es.EsCql2DslQueryParser;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kesc
 * @since 2024-02-02 10:06
 */
@Slf4j
public class QueryLanguageParsers {
    private static final Map<LanguageType, Map<ParserType, AbstractQueryParser<?>>> PARSER_MAP = new HashMap<>();

    static {
        register(new NoopCqlQueryParser());
        register(new NoopSqlQueryParser());
        register(new EsCql2DslQueryParser());
    }

    public static <T> void register(AbstractQueryParser<T> parser) {
        Map<ParserType, AbstractQueryParser<?>> map = PARSER_MAP.computeIfAbsent(parser.language(), k -> new HashMap<>());
        map.put(parser.parserType(), parser);
    }

    public static AbstractQueryParser<?> getParser(LanguageType languageType, ParserType parserType) {
        AbstractQueryParser<?> queryParser = PARSER_MAP.getOrDefault(languageType, Collections.emptyMap()).get(parserType);
        return Objects.requireNonNull(queryParser, String.format("language:%s parser:%s not found, please register first.", languageType, parserType));
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <T> AbstractQueryParser<T> getParser(LanguageType languageType, ParserType parserType, Class<T> clazz) {
        return (AbstractQueryParser<T>) getParser(languageType, parserType);
    }

}
