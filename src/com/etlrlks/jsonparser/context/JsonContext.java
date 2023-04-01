package com.etlrlks.jsonparser.context;

/**
 * Created on 2022/12/4.
 * 是一个上下文对象，用于存储当前解析位置和 JSON 字符串。
 * 在解析过程中，通过更新 JsonContext 的 pos 属性来跟踪解析位置，从而实现逐个字符地解析 JSON 字符串
 * @author etlRlks
 */
public class JsonContext {
    public String json;
    public int pos;

    public JsonContext(String json) {
        this.json = json;
        this.pos = 0;
    }

    public char currentChar() {
        return this.json.charAt(this.pos);
    }

    public char nextChar() {
        return this.json.charAt(++this.pos);
    }

    public char peekChar() {
        return this.json.charAt(this.pos + 1);
    }
}
