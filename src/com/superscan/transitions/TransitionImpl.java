package com.superscan.transitions;

import com.superscan.states.State;

public final class TransitionImpl implements Transition {

    private Character rule;
    private State next;

    public TransitionImpl(Character rule, State next) {
        this.rule = rule;
        this.next = next;
    }

    public State next() { return this.next; }

    public boolean isValid(Character c) {
        return this.rule.equals(c);
    }

}
