package com.xjbg.query.parser.enums;

import lombok.Getter;

/**
 * @author kesc
 * @since 2024-01-29 10:27
 */
@Getter
public enum ParserType {
    NOOP("noop"),
    ES("es");
    private final String type;

    ParserType(String type) {
        this.type = type;
    }
}
