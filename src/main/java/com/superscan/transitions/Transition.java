package com.superscan.transitions;

import com.superscan.states.State;

public interface Transition {

    /**
     * Is this a valid transition, given c?
     * @param c Character.
     * @return true if valid, false otherwise.
     */
    boolean isValid(final Character c);

    /**
     * Return the next state from following this transition.
     * @return State.
     */
    State next();

}
