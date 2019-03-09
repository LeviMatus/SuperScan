/**
 * REFERENCES
 *
 * https://github.com/eugenp/tutorials/blob/master/algorithms-miscellaneous-1/src/main/java/com/baeldung/algorithms/automata/FiniteStateMachine.java
 *
 * The interface itself hasn't changed much. We enforce that we should be able to return the accepted tokens.
 */

package superscan.dfa;

import superscan.Token;
import superscan.dfa.InvalidTokenException;

import java.util.List;

public interface DFA {

    void transition(final Character c) throws InvalidTokenException;

    List<Token> getAcceptedTokens();

}
