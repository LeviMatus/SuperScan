package com.superscan.dfa;

import com.superscan.Token;

import java.util.List;

public interface DFA {

    void transition(final Character c) throws InvalidTokenException;

    List<Token> getAcceptedTokens();

}
