/**
 * REFERENCES
 *
 * https://github.com/eugenp/tutorials/blob/master/algorithms-miscellaneous-1/src/main/java/com/baeldung/algorithms/automata/RtFiniteStateMachine.java
 *
 * This has changed a lot. We have to keep track of far more information that does Baeldung.
 */

package com.superscan.dfa;

import com.superscan.Token;
import com.superscan.enums.KeywordEnum;
import com.superscan.enums.TokenEnum;
import com.superscan.states.State;

import java.util.*;

public final class DFAImpl implements DFA {

    private State current;
    private Integer start, lineNum, offset;
    private Token pendingToken;
    private List<Token> acceptedTokens;
    private boolean isAborting;
    private List<KeywordEnum> keywords;

    /**
     * Sets up a DFA given a starting state.
     * @param initial State implementation. Ideally should be of type InitialStateImpl
     */
    public DFAImpl(final State initial) {
        this.start = this.lineNum = 1;
        this.offset = 1;
        this.current = initial;
        this.pendingToken = new Token(this.start, this.lineNum);
        this.acceptedTokens = new ArrayList<>();
        this.keywords = new ArrayList<>(EnumSet.allOf(KeywordEnum.class));
    }

    /**
     *
     * @return List<Token> a list of accepted tokens.
     */
    public List<Token> getAcceptedTokens() {return this.acceptedTokens;}

    public Token getPendingToken() {
        return pendingToken;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     *
     * @return boolean flag indicating DFA status.
     */
    public boolean isAborting() {return isAborting;}

    public void abort() {isAborting = true;}

    /**
     * Position line col number and offset from the col to the
     * end of the previously handled token.
     */
    public void reset() {
        this.start += this.offset;
        this.offset = 1;
    }

    /**
     * Given a whitespace character, either reset after reading a token or
     * add a new line offset for newline chars. Delimit the old token.
     * @param c Character
     */
    public void handleWhitespace(Character c) {
        Optional<Map<String, Integer>> newlineOffset = this.pendingToken.handleMultilineStrings();
        if (c == null) {
            this.start += this.offset - 1;
            this.offset = 1;
        } else if (c.equals('\n')) {
            this.lineNum++;
            this.start = 1;
            this.offset = 1;
        } else if (newlineOffset.isPresent()) {
            this.lineNum = this.lineNum + newlineOffset.get().get("NLs");
            this.start = newlineOffset.get().get("OFFSET") + 1;
            this.offset = 1;
        } else
            reset();
        this.pendingToken = new Token(this.lineNum, this.start);
    }

    /**
     * Delimit a token if its char construction is longer than 0.
     */
    public void delimitToken(Character c) {
        if (this.getPendingToken().getType().equals(TokenEnum.IDENTIFIER)) {
            keywords.stream()
                    .filter(keywordEnum -> keywordEnum.isKeyword(this.pendingToken.getVal()))
                    .findAny()
                    .ifPresent((keywordEnum -> pendingToken.setType(keywordEnum.getToken())));
        }
        this.acceptedTokens.add(this.pendingToken);
        handleWhitespace(c);
    }

    /**
     * Return a throwable InvalidTokenException providing the current token's toString as an error message.
     * @return InvalidTokenException
     */
    public InvalidTokenException generateError() {
        return new InvalidTokenException(this.pendingToken.toString());
    }

    /**
     * Mutate the pending token, adding a char to it and incrementing the line's col offset.
     * @param c Char
     */
    public void addCharToToken(Character c) {
        this.pendingToken.addChar(c);
        this.offset++;
    }

    /**
     * Transition the DFA from reading a character.
     *
     * @param c Character. Next char from the char stream.
     * @throws InvalidTokenException if the DFA does not accept a token.
     */
    public void transition(final Character c) throws InvalidTokenException {
        try {
            this.current = this.current.transition(c, this);
        } catch (IllegalArgumentException e) {
            addCharToToken(c);
            if (!this.isAborting) this.isAborting = true;
            if (this.pendingToken.getVal().length() == 1) throw generateError();
        }
    }

}
