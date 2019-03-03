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
        this.start += this.offset;
        this.offset = 1;
    }

    public void handleWhitespace(Character c) {
        if (c.equals('\n')) {
            this.lineNum++;
            this.start = 1;
            this.offset = 1;
        } else reset();
        this.pendingToken = new Token(this.lineNum, this.start);
    }

    public void delimitToken() {
        this.pendingToken.setType(this.current.getTokenType());
        this.acceptedTokens.add(this.pendingToken);
    }

    public boolean isValidToken(Tokens token) {
        return !token.equals(Tokens.INDETERMINATE) && !token.equals(Tokens.INVALID);
    }

    public InvalidTokenException generateError() {
        return new InvalidTokenException(this.pendingToken.toString());
    }

    public void addCharToToken(Character c) {
        this.pendingToken.addChar(c);
        this.offset++;
    }

    public DFA transition(final Character c) throws InvalidTokenException {
        try {
            this.current = this.current.transition(c, this);
        } catch (IllegalArgumentException e) {
            addCharToToken(c);
            if (!this.isAborting) this.isAborting = true;
            if (this.pendingToken.getVal().length() == 1) throw generateError();
        }
        return this;
    }

    public List<Token> getAcceptedTokens() {
        return this.acceptedTokens;
    }
    public void abort() {isAborting=true;}

    public boolean isAborting() {
        return isAborting;
    }
}
