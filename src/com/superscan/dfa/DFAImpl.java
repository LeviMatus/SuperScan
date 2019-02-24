package com.superscan.dfa;

import com.superscan.Token;
import com.superscan.states.State;
import com.superscan.enums.Tokens;

import java.util.ArrayList;
import java.util.List;

public final class DFAImpl implements DFA {

    private State current, initial;
    private Integer start, lineNum, offset;
    private Token pendingToken;
    private List<Token> acceptedTokens;
    private boolean isAborting;

    public DFAImpl(final State initial) {
        this.start = this.lineNum = 1;
        this.offset = 1;
        this.current = this.initial = initial;
        this.pendingToken = new Token(this.start, this.lineNum);
        this.acceptedTokens = new ArrayList<>();
    }

    private void reset() {
        this.current = this.initial;
        this.start += this.offset;
        this.offset = 1;
    }

    private void handleWhitespace(Character c) {
        if (c.equals('\n')) {
            this.lineNum++;
            this.start = 1;
            this.offset = 1;
        } else reset();
    }

    private void delimitToken() {
        this.pendingToken.setType(this.current.getTokenType());
        this.acceptedTokens.add(this.pendingToken);
        this.current = this.initial;
    }

    private boolean isValidToken(Tokens token) {
        return !token.equals(Tokens.INDETERMINATE) && !token.equals(Tokens.INVALID);
    }

    private InvalidTokenException generateError() {
        return new InvalidTokenException(this.pendingToken.toString());
    }

    public DFA transition(final Character c) throws InvalidTokenException {
        if (Character.isWhitespace(c)) {
            if (this.isAborting) throw generateError();

            if (!this.current.equals(this.initial)) {
                if (isValidToken(this.current.getTokenType())) {
                    delimitToken();
                    handleWhitespace(c);
                    this.pendingToken = new Token(this.lineNum, this.start);
                    return this;
                }
                return this;
            }
        }
        this.pendingToken.addChar(c);
        this.offset++;

        try {
            this.current = this.current.transition(c);
        } catch (IllegalArgumentException e) {
            if (!this.isAborting) this.isAborting = true;
            if (this.pendingToken.getVal().length() == 1) throw generateError();
        }

        return this;
    }

    public List<Token> getAcceptedTokens() {
        return this.acceptedTokens;
    }

}
