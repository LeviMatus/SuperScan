package com.superscan;

import com.superscan.states.State;

public final class FSMImpl implements FSM {

    private State current, initial;
    private Integer start, lineNum, offset;

    public FSMImpl(final State initial) {
        this.start = this.lineNum = 1;
        this.offset = 0;
        this.current = this.initial = initial;
    }

    public void incrementOffset() { this.offset++; }
    public void incrementLineNum() { this.lineNum++; }

    public void reset() {
        this.current = this.initial;
        this.start += this.offset;
        this.offset = 1;
    }

    public FSM transition(final Character c) {
        return new FSMImpl(this.current.transition(c));
    }

    public boolean canStop() {
        return this.current.isFinal();
    }

}
