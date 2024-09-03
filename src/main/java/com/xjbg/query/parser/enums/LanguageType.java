package com.xjbg.query.parser.enums;

import lombok.Getter;

/**
 * @author kesc
 * @since 2024-01-29 10:29
 */
@Getter
public enum LanguageType {
    CQL("cql"),
    SQL("sql");
    private final String language;

    LanguageType(String language) {
        this.language = language;
    }

}
