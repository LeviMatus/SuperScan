package com.superscan.dfa;

public interface FSM {

    FSM transition(final Character c);

    boolean isSatisfied();

}
