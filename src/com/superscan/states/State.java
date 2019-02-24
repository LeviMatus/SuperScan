package com.superscan.states;

import com.superscan.transitions.Transition;

public interface State {

    /**
     * This State is an accepting state.
     * @return true if accepted, false otherwise.
     */
    boolean isFinal();

    /**
     * Add a Transition to the calling State
     * @param transition Transition object representing the transition from State A to State B
     * @return
     */
    State addTransition(final Transition transition);

    /**
     * Given Character c, transition to a the next valid State.
     * @param c Character which to read next.
     * @return Accepted State.
     * @throws IllegalStateException if c is invalid.
     */
    State transition(final Character c);
}
