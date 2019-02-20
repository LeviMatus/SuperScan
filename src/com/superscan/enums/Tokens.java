package com.superscan.enums;

public enum Tokens {
    INVALID("INVALID"),
    INDETERMINATE("INDETERMINATE"),
    STRING("STRING");

    private String name;

    @Override
    public String toString() {return name;}

    Tokens(String name) {this.name = name;}
}
