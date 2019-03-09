/**
 * REFERENCES
 *
 * https://github.com/eugenp/tutorials/blob/master/algorithms-miscellaneous-1/src/main/java/com/baeldung/algorithms/automata/State.java
 *
 * This hasn't changed much. Most of the heavy logic changes we placed in the Abstract State class.
 * We have added a default method here shared between all State implementations
 */

package superscan.states;

import superscan.dfa.DFAImpl;
import superscan.dfa.InvalidTokenException;
import superscan.enums.TokenEnum;
import superscan.transitions.Transition;

public interface State {

    TokenEnum getTokenType();

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
    void addTransition(final Transition transition);

    /**
     * Given Character c, transition to a the next valid State.
     * @param c Character which to read next.
     * @return Accepted State.
     * @throws IllegalStateException if c is invalid.
     */
    State transition(final Character c, final DFAImpl dfa) throws InvalidTokenException;

    default boolean isValidToken(TokenEnum token) {
        return !token.equals(TokenEnum.INDETERMINATE) && !token.equals(TokenEnum.INVALID);
    }
}
