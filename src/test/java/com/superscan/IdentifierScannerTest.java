package com.superscan;

import com.superscan.enums.TokenEnum;
import com.superscan.states.State;
import com.superscan.states.StateImpl;
import com.superscan.transitions.TransitionImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierScannerTest extends AbstractScannerTest<String> {

    private static State S76;

    private static void addUpperAndLowercase(State state, State next, int i) {
        state.addTransition(new TransitionImpl((char) ('a' + i), next));
        state.addTransition(new TransitionImpl((char) ('A' + i), next));
    }

    @BeforeAll
    static void constructStates() {
        State S76 = new StateImpl(true, TokenEnum.IDENTIFIER, "S76");

        for (int i = 0; i < 26; i++) {
            addUpperAndLowercase(getS1(), S76, i);
            addUpperAndLowercase(S76, S76, i);
        }
    }

    @Test
    void valid() {
        characters.add('a');
        characters.add('b');
        characters.add('c');
        characters.add(' ');

        assertTestCase("'abc' should be a valid IDENTIFIER");
    }

    @Override
    void assertTestCase(String message) {
        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(1, dfa.getAcceptedTokens().size(), "There should be 1 accepted token.");
        assertEquals(TokenEnum.IDENTIFIER, dfa.getAcceptedTokens().get(0).getType(), message);
    }
}
