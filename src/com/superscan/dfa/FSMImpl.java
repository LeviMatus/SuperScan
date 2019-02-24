package com.superscan.dfa;

import com.superscan.Token;
import com.superscan.states.State;
import com.superscan.enums.Tokens;
import com.superscan.transitions.InvalidTokenException;

import java.util.ArrayList;
import java.util.List;

public final class FSMImpl implements FSM {

    private State current, initial;
    private Integer start, lineNum, offset;
    private Token pendingToken;
    private List<Token> acceptedTokens;
    private boolean isAborting;

    public FSMImpl(final State initial) {
        this.start = this.lineNum = 1;
        this.offset = 1;
        this.current = this.initial = initial;
        pendingToken = new Token(start, lineNum);
        acceptedTokens = new ArrayList<>();
    }

    public void incrementOffset() { this.offset++; }
    public void incrementLineNum() { this.lineNum++; }

    public void reset() {
        this.current = this.initial;
        this.start += this.offset;
        this.offset = 1;
    }

    private void handleWhitespace(Character c) {
        if (c.equals('\n')) {
            lineNum++;
            start = 1;
            offset = 1;
        } else reset();
    }

    public void delimitToken() {
        pendingToken.setType(current.getTokenType());
        acceptedTokens.add(pendingToken);
        current = initial;
    }

    private boolean isValidToken(Tokens token) {
        return !token.equals(Tokens.INDETERMINATE) && !token.equals(Tokens.INVALID);
    }

    public FSM transition(final Character c) throws InvalidTokenException {
        if (Character.isWhitespace(c)) {
            if (this.isAborting) throw new InvalidTokenException("NOPE");

            if (!current.equals(initial)) {
                if (isValidToken(current.getTokenType())) {
                    delimitToken();
                    handleWhitespace(c);
                    pendingToken = new Token(lineNum, start);
                    return this;
                }
                return this;
            }
        }
        pendingToken.addChar(c);
        offset++;

        try {
            this.current = this.current.transition(c);
        } catch (IllegalArgumentException e) {
            if (!isAborting) this.isAborting = true;
        }

        return this;
    }

    public List<Token> getTokens() {
        return this.acceptedTokens;
    }

    public boolean isSatisfied() {
        return this.pendingToken.getVal().length() == 0;
    }

}
