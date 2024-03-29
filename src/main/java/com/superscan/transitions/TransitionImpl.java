package com.superscan.transitions;

import com.superscan.states.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TransitionImpl implements Transition {

    private Character rule;
    private List<Character> rules;
    private State next;

    public TransitionImpl(Character rule, State next) {
        this.rule = rule;
        this.next = next;
        this.rules = null;
    }

    /**
     * Constructor for handling negative relationships (char not equal to a or b). These must succeed together
     * or unexpected behavior may result.
     *
     * @param rules List<Character> with Character that shouldn't match given inputs.
     * @param next The next state to move to if an input is valid.
     */
    public TransitionImpl(State next, Character... rules) {
        assert(rules.length > 0);
        this.rule = null;
        this.next = next;
        this.rules = new ArrayList<>(Arrays.asList(rules));
    }

    public State next() { return this.next; }

    public boolean isValid(Character c) {
        if (this.rule != null) return this.rule.equals(c);

        for (Character r : rules) {
            if (r.equals(c)) return false;
        }
        return true;
    }

}
