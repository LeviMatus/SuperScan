/**
 * REFERENCES
 *
 * https://github.com/eugenp/tutorials/blob/master/algorithms-miscellaneous-1/src/main/java/com/baeldung/algorithms/automata/RtState.java
 *
 * This has changed a lot. We required the ability to have different transitions and delimitation techniques depending
 * on what type of char was being read. We abstracted out base logic from the original state implementation and then
 * made different concrete state implementations. This simplified handling initial states reading chars, comments states,
 * and standard states.
 *
 * This implementation is not extremely clean. We should have given states more control over delimitation, but
 * with time we sort of pigeonholed ourselves into this setup. It would be a good refactoring practice to come in here
 * and consolidate some of the delimitation logic with the DFA as well.
 */

package com.superscan.states;

import com.superscan.dfa.DFAImpl;
import com.superscan.dfa.InvalidTokenException;
import com.superscan.enums.CharTypeEnum;
import com.superscan.enums.TokenEnum;
import com.superscan.transitions.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractState implements State {

    private List<Transition> transitions;
    private boolean isFinal;
    private TokenEnum tokenType;
    private static State initialState;
    private static Map<TokenEnum, State> singleCharStates = new HashMap<>();

    AbstractState(final boolean isFinal, final TokenEnum tokenType) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
        this.tokenType = tokenType;

        if (tokenType.getTokenCharType().equals(CharTypeEnum.SINGLE)) {
            singleCharStates.put(tokenType, this);
        }
    }

    public boolean isFinal() { return this.isFinal; }
    public TokenEnum getTokenType() { return this.tokenType; }

    /**
     * @return State the initial state of the DFA
     */
    static State getInitialState() {
        return initialState;
    }

    static Map<TokenEnum, State> getSingleCharStates() {
        return singleCharStates;
    }

    // Set initial state for all states
    static void setInitialState(State initialState) {
        AbstractState.initialState = initialState;
    }

    /**
     * Add a transition to the state.
     * @param transition Transition object representing the transition from State A to State B
     * @return this State
     */
    public void addTransition(Transition transition) {
        this.transitions.add(transition);
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
        // Search all of this states transitions and find one that matches.
        State result = transitions
                .stream()
                .filter(transition -> transition.isValid(c))
                .map(Transition::next)
                .findAny()
                .orElseGet(() -> attemptFallback(c, dfa));

         // If this is a single-char accepting state then immediately delimit it. Other chars are allowed to follow.
        if (result.getTokenType().getTokenCharType().equals(CharTypeEnum.SINGLE)) {
            if (!this.equals(initialState)) {
                delimitSingleCharacterToken(c, dfa);
                singleCharStates.get(result.getTokenType());
            }
            return delimitSingleCharacterToken(c, result, dfa);
        }

        // Standard delimitation case. Whitespace.
        if (result.equals(initialState) )
            return delimitWithWhitespace(c, result, dfa);

        dfa.addCharToToken(c);
        return result;
    }

    // Fallback to a state upon errors to finish parsing Multi-Char tokens.
    public State attemptFallback(final Character c, final DFAImpl dfa) {
        throw new IllegalArgumentException("FAILURE");
    }

    /**
     * Delimit tokens on whitespace if not originating from initial state. If the DFA is now finished tokenizing an
     * invalid multi-char token, then it throws an error and finishes execution. Otherwise, assuming the token is valid,
     * the DFA delimits the token and continues.
     *
     * @param c Character being read
     * @param state State being transitioned to.
     * @param dfa DFA to which the state belongs
     * @return state
     * @throws InvalidTokenException if the multi-char token is invalid and the DFA has been flagged so already.
     */
    public State delimitWithWhitespace(final Character c, final State state, final DFAImpl dfa) throws InvalidTokenException {
        if (dfa.isAborting()) throw dfa.generateError();
        if (isValidToken(this.tokenType)) {
            dfa.delimitToken();
            dfa.handleWhitespace(c);
        }
        else throw dfa.generateError();
        return state;
    }

    // special case where singe char token is being read after another token. Offset should not increase.
    private void delimitSingleCharacterToken(final Character c, final DFAImpl dfa) {
        dfa.delimitToken();
        dfa.decrementOffset();
        dfa.handleWhitespace(c);
    }

    // singe-char token is read from the initial state. Offset should increase.
    private State delimitSingleCharacterToken(final Character c, final State state, final DFAImpl dfa) {
        dfa.addSingleCharToken(c, state);
        dfa.handleWhitespace(c);
        return initialState;
    }

}