package com.superscan.enums;

import java.security.Key;

public enum KeywordEnum {
    QOUTE(TokenEnum.MULTI_QUOTE, "qoute"),
    LAMNDA(TokenEnum.LAMBDA, "lambda"),
    DEFINE(TokenEnum.DEFINE, "define"),
    LET(TokenEnum.LET, "let"),
    COND(TokenEnum.COND, "cond"),
    IF(TokenEnum.IF, "if"),
    BEGIN(TokenEnum.BEGIN, "begin");

    private final TokenEnum token;
    private final String keyword;

    KeywordEnum(TokenEnum token, String keyword) {
        this.token = token;
        this.keyword = keyword;
    }

    public boolean isKeyword(String token) {
        return this.keyword.equals(token);
    }

    public TokenEnum getToken() {return this.token;}

}
