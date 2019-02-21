package com.superscan.utils;

public class CharUtils {

    public static int charToASCII(final Character character) {
        return (int) character;
    }

    public static String charToOctal(final Character character) {
        return Integer.toOctalString(character);
    }

}
