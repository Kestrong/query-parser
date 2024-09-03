# 自定义语法解释器

通过antlr4框架定义分词规则和语法规则，解析输入的表达式片段并转换成目标语法内容。

## CQL（推荐）

### 语法规则

| 语法        | 说明                                                                                                                     |
|-----------|------------------------------------------------------------------------------------------------------------------------|
| key:value | 键值检索，查询字段（key）的值中包含 value 的日志，例如：level:ERROR                                                                           |
| value     | 全文检索，查询日志全文中包含 value的 日志，例如：ERROR                                                                                      |
| AND       | “与”逻辑操作符，不区分大小写，例如：level:ERROR AND pid:1234                                                                            |
| OR        | “或”逻辑操作符，不区分大小写，例如：level:ERROR OR level:WARNING                                                                        |
| NOT       | “非”逻辑操作符，不区分大小写，例如：level:ERROR NOT pid:1234                                                                            |
| ()        | 逻辑分组操作符，控制逻辑运算优先级，例如：level:(ERROR OR WARNING) AND pid:1234 未使用括号时，AND优先级高于OR                                           |            
| "  "      | 短语检索，使用双引号包裹一个字符串，日志需包含字符串内的各个词，且各个词的顺序保持不变，例如：name:"john Smith" 短语检索中不存在逻辑操作符，其等同于查询字符本身，例如：name:"and"                |           
| '  '      | 短语检索，使用单引号包裹一个字符串，功能等价于""，当被检索短语中包含双引号时，可使用单引号包裹该短语，以避免语法错误，例如：body:'user_name:"bob"'                                  | 
| *         | 模糊检索，匹配零个、单个、多个字符，例如：host:www.test*.com                                                                                |
| >         | 范围操作符，表示大于某个数值，例如：status>400 或 status:>400                                                                             |
| >=        | 范围操作符，表示大于等于某个数值，例如：status>=400或status:>=400                                                                           |
| <         | 范围操作符，表示小于某个数值，例如：status<400或status:<400                                                                               |
| <=        | 范围操作符，表示小于等于某个数值，例如：status<=400或status:<=400                                                                           |
| =         | 范围操作符，表示等于某个数值，例如：status=400，等价于status:400                                                                             |
| key:*     | text 类型字段：查询字段（key）存在的日志，无论值是否为空，例如：url:* long/double 类型字段：查询字段（key）存在，且值为数值的日志，例如：response_time:*                     |               
| key:""    | text 类型字段：查询字段（key）存在且值为空的日志，值仅包含分词符时也等价为空，例如：url:"" long/double 类型字段：查询字段值不为数值的日志，包含字段(key)不存在的情况，例如：response_time:"" |

| 示例                        | 语句                                            |
|---------------------------|-----------------------------------------------|
| 检索来源为某台机器的日志              | __SOURCE__:127.0.0.1 或 __SOURCE__:192.168.0.* |
| 检索来源为某个文件的日志              | __FILENAME__:"/var/log/access.log"            |
| 检索包含 ERROR 的日志            | ERROR                                         |
| 检索失败的日志（状态码大于400）         | status>400                                    |
| 检索 GET 请求中失败（状态码大于400）的日志 | method:GET AND status>400                     |
| 检索 ERROR 或 WARNING 级别的日志  | level:(ERROR OR WARNING)                      |
| 检索非 INFO 级别 的日志           | NOT level:INFO                                |

**短语检索**

使用双引号或单引号包裹一个字符串进行检索，例如`name:"john Smith"`、`filepath:"/var/log/access.log"`。与不使用引号包裹的检索相比，短语检索表示日志需在包含字符串内各个词的同时，词之间的顺序也与检索条件严格一致。
例如有如下两条日志，分词符为`/`：

```
#1 filepath:"/var/log/access.log"
#2 filepath:"/log/var/access.log"
```

使用`filepath:/var/log/access.log`进行检索时会同时检索到上述两条日志，因为非短语检索不要求词之间的顺序。
使用`filepath:"/var/log/access.log"`进行检索时仅会检索到第一条日志。
短语检索的检索条件更加严格，检索较长的字符串时，建议使用短语检索。

**模糊检索**

模糊检索需要在词的中间或末尾加上通配符，可使用`*`匹配零个、单个、多个字符，例如：
`IP:192.168.1.*` 可检索到`192.168.1.1`、`192.168.1.34`等。
`host:www.te*t.com` 可检索到 `www.test.com`、`www.telt.com` 等。

**注意**

* long 和 double 类型字段不支持使用*进行模糊检索，可以使用数值范围进行检索，例如：`status>400 and status<500`。

### 代码样例

更多内容请查看单元测试用例
```
    ...
    AbstractQueryParser<?> parser = QueryLanguageParsers.getParser(LanguageType.CQL, ParserType.ES);

    @Test
    public void testMatchAll() {
        String expr = "*";
        Object result = parser.parse(expr);
        log.info("{}", result);
    }
    ...
```

## SQL（规划中）