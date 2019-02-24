package com.superscan.dfa;

import com.superscan.transitions.InvalidTokenException;

public interface FSM {

    FSM transition(final Character c) throws InvalidTokenException;

}
