package com.superscan.enums;

public enum TokenEnum {
    INVALID("INVALID", CharTypeEnum.NA),
    INDETERMINATE("INDETERMINATE", CharTypeEnum.NA),
    STRING("STRING", CharTypeEnum.MULTI),
    CHAR("CHAR", CharTypeEnum.MULTI),
    NUMBER("NUMBER", CharTypeEnum.MULTI),
    MULTI_QUOTE("QUOTE", CharTypeEnum.MULTI),
    SINGLE_QOUTE("QUOTE", CharTypeEnum.SINGLE),
    OPENRD("OPENRD", CharTypeEnum.SINGLE),
    CLOSERD("CLOSERD", CharTypeEnum.SINGLE),
    OPENSQ("OPENSQ", CharTypeEnum.SINGLE),
    CLOSESQ("CLOSESQ", CharTypeEnum.SINGLE),
    OPENCU("OPENCU", CharTypeEnum.SINGLE),
    CLOSECU("CLOSECU", CharTypeEnum.SINGLE),
    BOOLEAN("BOOL", CharTypeEnum.MULTI),
    LAMBDA("LAMBDA", CharTypeEnum.MULTI),
    DEFINE("DEFINE", CharTypeEnum.MULTI),
    LET("LET", CharTypeEnum.MULTI),
    COND("COND", CharTypeEnum.MULTI),
    IF("IF", CharTypeEnum.MULTI),
    BEGIN("BEGIN", CharTypeEnum.MULTI),
    IDENTIFIER("IDENTIFIER", CharTypeEnum.MULTI);


    private final String name;
    private final CharTypeEnum type;

    @Override
    public String toString() {return name;}

    TokenEnum(String name, CharTypeEnum type) {this.name = name; this.type = type;}

    public CharTypeEnum getTokenCharType() {
        return type;
    }

}