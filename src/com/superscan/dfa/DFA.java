package com.superscan.dfa;

public interface DFA {

    DFA transition(final Character c) throws InvalidTokenException;

}
