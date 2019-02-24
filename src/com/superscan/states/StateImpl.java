package com.superscan.states;

import com.superscan.transitions.Transition;

import java.util.ArrayList;
import java.util.List;

public class StateImpl implements State {

    private List<Transition> transitions;
    private boolean isFinal;

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public StateImpl() {
        this(false);
    }

    public StateImpl(final boolean isFinal) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
    }

    public boolean isFinal() {return this.isFinal;}

    public State addTransition(Transition transition) {
        this.transitions.add(transition);
        return this;
    }

    public State transition(final Character c) {
        return transitions
                .stream()
                .filter(transition -> transition.isValid(c))
                .map(Transition::next)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("FAILURE"));
    }

}
