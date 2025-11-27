package com.puc.realconsult.utils;

import java.time.LocalDateTime;

public final class JsonUtil {

    private JsonUtil() {}

    // STRING
    public static void appendString(StringBuilder sb, String field, String value, boolean[] first) {
        fieldStart(sb, field, first);
        if (value == null) {
            sb.append("null");
        } else {
            sb.append("\"").append(escape(value)).append("\"");
        }
    }

    // NUMBER
    public static void appendNumber(StringBuilder sb, String field, Number value, boolean[] first) {
        fieldStart(sb, field, first);
        if (value == null) {
            sb.append("null");
        } else {
            sb.append(value);
        }
    }

    // BOOLEAN
    public static void appendBoolean(StringBuilder sb, String field, Boolean value, boolean[] first) {
        fieldStart(sb, field, first);
        if (value == null) {
            sb.append("null");
        } else {
            sb.append(value ? "true" : "false");
        }
    }

    // JSON OU TEXTO
    public static void appendJsonOrString(StringBuilder sb, String field, String value, boolean[] first) {
        fieldStart(sb, field, first);
        if (value == null) {
            sb.append("null");
        } else if (looksLikeJson(value)) {
            sb.append(value);
        } else {
            sb.append("\"").append(escape(value)).append("\"");
        }
    }

    // DATETIME
    public static void appendDateTime(StringBuilder sb, String field, LocalDateTime value, boolean[] first) {
        fieldStart(sb, field, first);
        if (value == null) {
            sb.append("null");
        } else {
            sb.append("\"").append(value).append("\"");
        }
    }

    private static void fieldStart(StringBuilder sb, String field, boolean[] first) {
        if (!first[0]) sb.append(",\n");
        sb.append("  \"").append(field).append("\": ");
        first[0] = false;
    }

    public static boolean looksLikeJson(String value) {
        if (value == null) return false;
        String v = value.trim();
        return (v.startsWith("{") && v.endsWith("}")) ||
                (v.startsWith("[") && v.endsWith("]"));
    }

    public static String escape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }
}
