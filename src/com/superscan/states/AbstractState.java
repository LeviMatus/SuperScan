package com.superscan.states;

import com.superscan.dfa.DFAImpl;
import com.superscan.dfa.InvalidTokenException;
import com.superscan.enums.Tokens;
import com.superscan.transitions.Transition;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractState implements State {

    private List<Transition> transitions;
    private boolean isFinal;
    private Tokens tokenType;
    private static State initialState;

    public AbstractState(final boolean isFinal, final Tokens tokenType) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
        this.tokenType = tokenType;
    }

    public static State getInitialState() {
        return initialState;
    }

    public static void setInitialState(State initialState) {
        AbstractState.initialState = initialState;
    }

    public State addTransition(Transition transition) {
        this.transitions.add(transition);
        return this;
    }

    public State transition(final Character c, final DFAImpl dfa) throws InvalidTokenException {
        State result = transitions
                .stream()
                .filter(transition -> transition.isValid(c))
                .map(Transition::next)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("FAILURE"));

        if (result.equals(initialState)) {
            if (dfa.isAborting()) throw dfa.generateError();
            if (isValidToken(this.tokenType)) {
                dfa.handleWhitespace(c);
            }
            return result;
        }

        dfa.addCharToToken(c);
        return result;
    }

    public boolean isFinal() {return this.isFinal;}
    public Tokens getTokenType() { return this.tokenType; }

    public List<Transition> getTransitions() {
        return transitions;
    }
}
