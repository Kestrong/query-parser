package com.xjbg.query.parser.utils;

/**
 * @author kesc
 * @since 2024-09-03 16:05
 */
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public final class StringUtil extends StringUtils {
    public static final String COMMA = ",";
    public static final String UNDER_LINE = "_";
    public static final String DOT = ".";
    public static final String MINUS = "-";
    public static final String VIRGULE = "/";
    public static final String COLON = ":";
    public static final String PLUS = "+";
    public static final String EQUIVALENT = "=";
    public static final String GT = ">";
    public static final String LT = "<";
    public static final String VERTICAL_LINE = "|";
    public static final String STAR = "*";
    public static final String PERCENT = "%";
    public static final String AND = "&";
    public static final String DOLOR = "$";
    public static final String L_QUOT = "(";
    public static final String R_QUOT = ")";
    public static final String SEMICOLON = ";";
    public static final String AT = "@";
    public static final String EXCLAMATION = "!";

    public StringUtil() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String latterUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String camel2Underline(String str) {
        if (isBlank(str)) {
            return str;
        } else {
            str = str.trim();
            StringBuilder result = new StringBuilder(str.length());
            result.append(Character.toLowerCase(str.charAt(0)));

            for(int i = 1; i < str.length(); ++i) {
                char ch = str.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append('_').append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }

            return result.toString();
        }
    }

    public static String underline2Camel(String str) {
        if (isBlank(str)) {
            return str;
        } else {
            str = str.trim();
            StringBuilder result = new StringBuilder(str.length());
            boolean flag = false;

            for(int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);
                if ('_' == ch) {
                    flag = true;
                } else if (flag) {
                    result.append(Character.toUpperCase(ch));
                    flag = false;
                } else {
                    result.append(ch);
                }
            }

            return result.toString();
        }
    }

    public static byte[] getBytes(String src) {
        return src == null ? null : src.getBytes();
    }

    public static byte[] getBytes(String src, String charset) {
        return getBytes(src, Charset.forName(charset));
    }

    public static byte[] getBytes(String src, Charset charset) {
        return src == null ? null : src.getBytes(charset);
    }

    public static byte[] getUTF8Bytes(String src) {
        return getBytes(src, StandardCharsets.UTF_8);
    }

    public static String valueOf(Object o) {
        return o == null ? null : String.valueOf(o);
    }
}
