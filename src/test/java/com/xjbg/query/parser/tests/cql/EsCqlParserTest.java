package com.xjbg.query.parser.tests.cql;

import com.xjbg.query.parser.base.AbstractQueryParser;
import com.xjbg.query.parser.base.QueryLanguageParsers;
import com.xjbg.query.parser.enums.LanguageType;
import com.xjbg.query.parser.enums.ParserType;
import org.junit.Test;

/**
 * @author kesc
 * @since 2024-02-02 10:50
 */
public class EsCqlParserTest extends AbstractCqlParserTest {
    AbstractQueryParser<?> parser = QueryLanguageParsers.getParser(LanguageType.CQL, ParserType.ES);

    @Test
    public void testMatchAll() {
        String expr = "*";
        Object result = parser.parse(expr);
        log.info("{}", result);
    }

    @Test
    public void testValueOnly() {
        String expr = "value";
        Object result = parser.parse(expr);
        log.info("{}", result);
    }

    @Test
    public void testMultiOrInShort() {
        String expr = "country:[中国|美国|日本]";
        Object result = parser.parse(expr);
        log.info("{}", result);
    }

    @Test
    public void testEq() {
        String expr1 = "country:中国";
        Object result1 = parser.parse(expr1);
        log.info("expr1:{}", result1);

        String expr2 = "country=中国";
        Object result2 = parser.parse(expr2);
        log.info("expr2:{}", result2);

        String expr3 = "people>100";
        Object result3 = parser.parse(expr3);
        log.info("expr3:{}", result3);

        String expr4 = "people>=100";
        Object result4 = parser.parse(expr4);
        log.info("expr4:{}", result4);

        String expr5 = "people<100";
        Object result5 = parser.parse(expr5);
        log.info("expr5:{}", result5);

        String expr6 = "people<=100";
        Object result6 = parser.parse(expr6);
        log.info("expr6:{}", result6);

        String expr7 = "country=\"中国\"";
        Object result7 = parser.parse(expr7);
        log.info("expr7:{}", result7);

        String expr8 = "country:中国*";
        Object result8 = parser.parse(expr8);
        log.info("expr8:{}", result8);

        String expr9 = "country:*";
        Object result9 = parser.parse(expr9);
        log.info("expr9:{}", result9);

        String expr10 = "country=\"\"";
        Object result10 = parser.parse(expr10);
        log.info("expr10:{}", result10);

        String expr11 = "country:\"中国\"";
        Object result11 = parser.parse(expr11);
        log.info("expr11:{}", result11);
    }

    @Test
    public void testBool() {
        String expr1 = "country:\"中国\" and city:\"厦门\"";
        Object result1 = parser.parse(expr1);
        log.info("expr1:{}", result1);

        String expr2 = "country=中国 or country=美国";
        Object result2 = parser.parse(expr2);
        log.info("expr2:{}", result2);

        String expr3 = "NOT country=中国";
        Object result3 = parser.parse(expr3);
        log.info("expr3:{}", result3);

        String expr4 = "country=中国 NOT people<100";
        Object result4 = parser.parse(expr4);
        log.info("expr4:{}", result4);

        String expr5 = "(country=中国 or country:美国) and people>100";
        Object result5 = parser.parse(expr5);
        log.info("expr5:{}", result5);
    }

    @Test
    public void testComplexExpr() {
        String expr5 = "(country=中国 or (country:美国 and company=[苹果,谷歌,亚马逊])) and people>100";
        Object result5 = parser.parse(expr5);
        log.info("expr5:{}", result5);
    }

}
