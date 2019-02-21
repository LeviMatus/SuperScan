package com.superscan.enums;

public enum Tokens {
    INVALID("INVALID"),
    INDETERMINATE("INDETERMINATE"),
    STRING("STRING"),
    CHAR("CHAR"),
    NUMBER("NUMBER"),
    MULTI_QUOTE("QUOTE"),
    SINGLE_QOUTE("QUOTE"),
    OPENRD("OPENRD"),
    CLOSERD("CLOSEDRD"),
    OPENSQ("OPENSQ"),
    CLOSESQ("CLOSESQ"),
    OPENCU("OPENCU"),
    CLOSECU("CLOSECU"),
    BOOLEAN("BOOL"),
    LAMBDA("LAMBDA"),
    DEFINE("DEFINE"),
    LET("LET"),
    COND("COND"),
    IF("IF"),
    BEGIN("BEGIN"),
    IDENTIFIER("IDENTIFIER");


    private String name;

    @Override
    public String toString() {return name;}

    Tokens(String name) {this.name = name;}
}
