# MyJsonParser
一个基于 Java 的简易的 JSON 解析器

## 使用
```
JsonCompiler.parse(json)
```
## 设计思路
- 定义 JsonType 和 JsonValue 类，其中 JsonType 枚举类型定义了 JSON 的数据类型，而 JsonValue 则代表 JSON 中的一个值，它包含一个 JsonType 类型的属性和一个 Object 类型的值，用于表示不同类型的 JSON 值。

- 定义 JsonContext 类，它是一个上下文对象，用于存储当前解析位置和 JSON 字符串。在解析过程中，通过更新 JsonContext 的 pos 属性来跟踪解析位置，从而实现逐个字符地解析 JSON 字符串。

- 实现 parseValue 方法，它根据当前字符的类型（数字、字符串、数组、对象等）调用相应的解析方法来解析 JSON 值。

- 实现 skipWhiteSpace 方法，它用于跳过 JSON 字符串中的空格、制表符、回车符等空白字符。

- 实现 parseNumber、parseString、parseBoolean、parseNull、parseArray 和 parseObject 等方法，它们分别用于解析不同类型的 JSON 值。其中，parseNumber 方法使用正则表达式来匹配 JSON 数字，并将其转换为 Java 的 double 类型；parseString 方法则处理 JSON 字符串的转义字符，并将其转换为 Java 的 String 类型；parseArray 和 parseObject 方法则递归调用 parseValue 方法来解析 JSON 数组和对象。
