package com.superscan;

public interface FSM {

    FSM transition(final Character c);

    boolean canStop();

}
