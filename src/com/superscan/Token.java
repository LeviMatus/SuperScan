package com.superscan;

import com.superscan.enums.Tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Token {

    private String val;
    private Integer row, col;
    private Tokens type;

    public Token(Integer row, Integer col) {
        val = "";
        this.row = row;
        this.col = col;
    }

    public String getVal() {
        return val;
    }

    public void addChar(Character c) {
        val += c;
    }

    public void setType(Tokens type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if (type == null || type.equals(Tokens.INVALID) || type.equals(Tokens.INDETERMINATE))
            return String.format("LEXICAL ERROR [%d:%d]: Invalid token `%s'",
                    row, col, val);
        return String.format("%s %d:%d", type.toString(), row, col);
    }

    public Optional<Map<String, Integer>> handleMultilineStrings() {
        Long number = this.val.chars()
                .mapToObj(e -> (char) e)
                .filter(e -> e.equals('\n'))
                .count();

        if (number > 0) {
            int i = this.val.lastIndexOf('\n');
            int len = this.val.substring(i).length();
            return Optional.of(
                    new HashMap<String, Integer>(){{
                        put("NLs", number.intValue());
                        put("OFFSET", len);
                    }});
        }
        return Optional.empty();
    }

}