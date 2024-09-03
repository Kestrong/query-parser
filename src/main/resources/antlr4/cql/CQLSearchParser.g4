// 同理，语法分析器的名称一定要和文件名保持一致
parser grammar CQLSearchParser;

options {
    // 表示解析token的词法解析器使用CQLSearchLexer
    tokenVocab = CQLSearchLexer;
}

// EOF(end of file)表示文件结束符，这个是Antlr中已经定义好的
cql: expression EOF;

expression:
    // 表示表达式可以被括号括起来
    // 如果语法后面加上了#{name}，相当于将这个name作为这个语法块的名字，这个#{name}要加都得加上，要不加都不加
    // (country:中国)
    LPAREN expression RPAREN                                                                       #lrExpr
    // leftExpr是给定义的语法起的别名(alias)，可有可无，但是有会更好点
    // 那样在java里面调用时就可以直接调用leftExpr和rightExpr，而不需要指定expressions中的索引(0或1)
    // country:中国
    | leftExpr = expression operator = (EQ|COLON|NE|GT|GTE|LT|LTE) rightExpr = expression          #eqExpr
    // (country:中国||country:美国)&&city:北京
    | leftExpr = expression operator = (BOOLAND|AND|BOOLOR|OR|NOT) rightExpr = expression          #boolExpr
    // NOT country:中国
    | leftExpr = NOT expression                                                                    #notExpr
    // (中国||美国)
    | LBRACKET item = ID (orItems)+ RBRACKET                                                       #orItemExpr
    // country等字面量
    | ID                                                                                           #identityExpr
    | STAR                                                                                         #identityExpr
;

orItems: operator = (BOOLOR|OR|COMMA) item = ID;