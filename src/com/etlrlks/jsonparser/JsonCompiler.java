package com.etlrlks.jsonparser;

import com.etlrlks.jsonparser.context.JsonContext;
import com.etlrlks.jsonparser.value.JsonValue;
import com.etlrlks.jsonparser.enums.JsonType;

import java.util.*;

/**
 * Created on 2022/12/1.
 *  一个基于 Java 的简易的 JSON 解析器
 * @author etlRlks
 */
public class JsonCompiler {
    public static JsonValue parse(String json) throws Exception {
        JsonContext context = new JsonContext(json);
        return parseValue(context);
    }

    /**
     * 根据当前字符的类型（数字、字符串、数组、对象等）
     * 调用相应的解析方法来解析 JSON 值
     * @param context
     * @return
     * @throws Exception
     */
    private static JsonValue parseValue(JsonContext context) throws Exception {
        char ch = context.currentChar();

        switch (ch) {
            case 'n':
                return parseNull(context);
            case 't':
                return parseTrue(context);
            case 'f':
                return parseFalse(context);
            case '\"':
                return parseString(context);
            case '[':
                return parseArray(context);
            case '{':
                return parseObject(context);
            default:
                return parseNumber(context);
        }
    }

    /**
     * 解析 NULL
     * @param context
     * @return
     * @throws Exception
     */
    private static JsonValue parseNull(JsonContext context) throws Exception {
        if (context.json.substring(context.pos, context.pos + 4).equals("null")) {
            context.pos += 4;
            return new JsonValue(JsonType.NULL, null);
        } else {
            throw new Exception("Invalid JSON format: " + context.json);
        }
    }

    /**
     * 解析 true
     * @param context
     * @return
     * @throws Exception
     */
    private static JsonValue parseTrue(JsonContext context) throws Exception {
        if (context.json.substring(context.pos, context.pos + 4).equals("true")) {
            context.pos += 4;
            return new JsonValue(JsonType.TRUE, true);
        } else {
            throw new Exception("Invalid JSON format: " + context.json);
        }
    }
    /**
     * 解析 false
     * @param context
     * @return
     * @throws Exception
     */
    private static JsonValue parseFalse(JsonContext context) throws Exception {
        if (context.json.substring(context.pos, context.pos + 5).equals("false")) {
            context.pos += 5;
            return new JsonValue(JsonType.FALSE, false);
        } else {
            throw new Exception("Invalid JSON format: " + context.json);
        }
    }

    /**
     * 使用正则表达式来匹配 JSON 数字
     * 并将其转为 Integer 或 Double
     * @param context
     * @return
     */
    private static JsonValue parseNumber(JsonContext context) {
        int start = context.pos;
        while (Character.isDigit(context.currentChar()) || context.currentChar() == '-' || context.currentChar() == '+' || context.currentChar() == '.' || context.currentChar() == 'e' || context.currentChar() == 'E') {
            context.pos++;
        }
        int end = context.pos;
        String str = context.json.substring(start, end);
        if (!str.contains(".")) {
            return new JsonValue(JsonType.NUMBER, Integer.parseInt(str));
        }
        return new JsonValue(JsonType.NUMBER, Double.parseDouble(str));
    }

    /**
     * 处理 JSON 字符串的转义字符
     * @param context
     * @return
     * @throws Exception
     */
    private static JsonValue parseString(JsonContext context) throws Exception {
        context.pos++;
        int start = context.pos;
        boolean escape = false;

        while ((context.currentChar() != '\"' || escape) && context.pos < context.json.length()) {
            escape = context.currentChar() == '\\' && !escape;
            context.pos++;
        }

        int end = context.pos;
        context.pos++;

        String str = context.json.substring(start, end);
        str = str.replaceAll("\\\\\"", "\"");
        str = str.replaceAll("\\\\n", "\n");
        str = str.replaceAll("\\\\r", "\r");
        str = str.replaceAll("\\\\t", "\t");
        str = str.replaceAll("\\\\f", "\f");
        str = str.replaceAll("\\\\b", "\b");
        str = str.replaceAll("\\\\\\\\", "\\");

        return new JsonValue(JsonType.STRING, str);
    }

    /**
     * 递归调用 parseValue 解析 JSON 数组
     * @param context
     * @return
     * @throws Exception
     */
    private static JsonValue parseArray(JsonContext context) throws Exception {
        context.pos++;

        List<JsonValue> list = new ArrayList<>();

        while (context.currentChar() != ']' && context.pos < context.json.length()) {
            skipWhiteSpace(context);

            if (context.currentChar() == ',') {
                context.pos++;
            } else {
                list.add(parseValue(context));
                skipWhiteSpace(context);

                if (context.currentChar() == ',') {
                    context.pos++;
                } else if (context.currentChar() != ']') {
                    throw new Exception("Invalid JSON format: " + context.json);
                }
            }
        }

        context.pos++;

        return new JsonValue(JsonType.ARRAY, list);
    }
    /**
     * 递归调用 parseValue 解析 JSON 对象
     * @param context
     * @return
     * @throws Exception
     */
    private static JsonValue parseObject(JsonContext context) throws Exception {
        context.pos++;

        Map<String, JsonValue> map = new HashMap<>();

        while (context.currentChar() != '}' && context.pos < context.json.length()) {
            skipWhiteSpace(context);

            if (context.currentChar() == ',') {
                context.pos++;
            } else {
                String key = parseString(context).value.toString();
                skipWhiteSpace(context);

                if (context.currentChar() != ':') {
                    throw new Exception("Invalid JSON format: " + context.json);
                }

                context.pos++;
                skipWhiteSpace(context);

                JsonValue value = parseValue(context);

                map.put(key, value);

                skipWhiteSpace(context);

                if (context.currentChar() == ',') {
                    context.pos++;
                } else if (context.currentChar() != '}') {
                    throw new Exception("Invalid JSON format: " + context.json);
                }
            }
        }

        context.pos++;

        return new JsonValue(JsonType.OBJECT, map);
    }

    /**
     * 用于跳过 JSON 字符串中的空格、制表符、回车符等空白字符
     * @param context
     */
    private static void skipWhiteSpace(JsonContext context) {
        while (Character.isWhitespace(context.currentChar())) {
            context.pos++;
        }
    }

}

