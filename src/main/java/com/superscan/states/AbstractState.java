package com.superscan.states;

import com.superscan.dfa.DFAImpl;
import com.superscan.dfa.InvalidTokenException;
import com.superscan.enums.CharTypeEnum;
import com.superscan.enums.TokenEnum;
import com.superscan.transitions.Transition;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractState implements State {

    private List<Transition> transitions;
    private boolean isFinal;
    private TokenEnum tokenType;
    private static State initialState;

    AbstractState(final boolean isFinal, final TokenEnum tokenType) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
        this.tokenType = tokenType;
    }

    public boolean isFinal() { return this.isFinal; }
    public TokenEnum getTokenType() { return this.tokenType; }

    /**
     * @return State the initial state of the DFA
     */
    static State getInitialState() {
        return initialState;
    }

    static void setInitialState(State initialState) {
        AbstractState.initialState = initialState;
    }

    /**
     * Add a transition to the state.
     * @param transition Transition object representing the transition from State A to State B
     * @return this State
     */
    public State addTransition(Transition transition) {
        this.transitions.add(transition);
        return this;
    }

    /**
     * Transition to a new state after reading a character. If we reach the initial state, then
     * delimit a token.
     *
     * @param c Character which to read next.
     * @param dfa DFA to update on transition
     * @return the new State
     * @throws InvalidTokenException on a non-accepted token.
     */
    @Override
    public State transition(final Character c, final DFAImpl dfa) throws InvalidTokenException {
        State result = transitions
                .stream()
                .filter(transition -> transition.isValid(c))
                .map(Transition::next)
                .findAny()
                .orElseGet(() -> attemptFallback(c, dfa));

        if (result.getTokenType().getTokenCharType().equals(CharTypeEnum.SINGLE))
            return handleSingeCharDelimitation(c, result, dfa);
        if (result.equals(initialState) )
            return handleDelimitation(c, result, dfa);

        dfa.addCharToToken(c);
        return result;
    }

    public State attemptFallback(final Character c, final DFAImpl dfa) {
        throw new IllegalArgumentException("FAILURE");
    }

    public State handleDelimitation(final Character c, final State state, final DFAImpl dfa) throws InvalidTokenException {
        if (dfa.isAborting()) throw dfa.generateError();
        if (isValidToken(this.tokenType)) {
            dfa.delimitToken();
            dfa.handleWhitespace(c);
        }
        else throw dfa.generateError();
        return state;
    }

    private State handleSingeCharDelimitation(final Character c, final State state, final DFAImpl dfa) {
        dfa.addSingleCharToken(c, state);
        dfa.handleWhitespace(c);
        return initialState;
    }

}