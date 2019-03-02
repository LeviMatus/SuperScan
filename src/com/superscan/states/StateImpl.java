package com.superscan.states;

import com.superscan.enums.Tokens;
import com.superscan.transitions.Transition;
import com.superscan.transitions.TransitionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StateImpl implements State {

    private List<Transition> transitions;
    private boolean isFinal;
    private Tokens tokenType;
    private static State initialState = null;

    public StateImpl(final boolean initial) {
        this(false, Tokens.INDETERMINATE);
        initialState = this;
    }

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

        // All states need whitespace transitions
        this.addTransition(new TransitionImpl(' ', initialState));
        this.addTransition(new TransitionImpl('\n', initialState));
        this.addTransition(new TransitionImpl('\t', initialState));
        //TODO: add more whitespace transitions if needed
    }

    public boolean isFinal() {return this.isFinal;}
    public Tokens getTokenType() { return this.tokenType; }

    public State addTransition(Transition transition) {
        this.transitions.add(transition);
        return this;
    }

    public State removeTransitionByRule(final Character c) {
        Predicate<Transition> ruleEquals = transition -> (transition.getRule().equals(c));
        this.transitions.removeIf(ruleEquals);
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
