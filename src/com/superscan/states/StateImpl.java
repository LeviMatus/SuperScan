package com.superscan.states;

import com.superscan.enums.Tokens;
import com.superscan.transitions.Transition;

import java.util.ArrayList;
import java.util.List;

public class StateImpl implements State {

    private List<Transition> transitions;
    private boolean isFinal;
    private Tokens tokenType;

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public StateImpl() {
        this(false, Tokens.INDETERMINATE);
    }

    public StateImpl(final boolean isFinal, final Tokens tokenType) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
        this.tokenType = tokenType;
    }

    public boolean isFinal() {return this.isFinal;}
    public Tokens getTokenType() { return this.tokenType; }

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
