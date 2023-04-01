package com.etlrlks.jsonparser.value;

import com.etlrlks.jsonparser.enums.JsonType;

/**
 * Created on 2022/12/5.
 * 用于表示不同类型的 JSON 值
 * @author etlRlks
 */
public class JsonValue {
    public JsonType type;
    public Object value;

    public JsonValue(JsonType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public JsonType getType() {
        return type;
    }

    public void setType(JsonType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
