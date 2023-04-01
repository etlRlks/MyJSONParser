package com.etlrlks.jsonparser;

import com.etlrlks.jsonparser.value.JsonValue;

/**
 * Created on 2023/4/1.
 *
 * @author etlRlks
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String json = "{ \"name\": \"etlrlks\", \"age\": 3}";

        JsonValue value = JsonCompiler.parse(json);

        System.out.println(value.type);
        System.out.println(value.value);
    }
}
