package com.superscan;

import com.superscan.enums.TokenEnum;
import com.superscan.states.State;
import com.superscan.states.StateImpl;
import com.superscan.transitions.TransitionImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooleanScannerTest extends AbstractScannerTest<Boolean> {

    private static State S20, S21;

    @BeforeAll
    static void constructStates() {
        S20 = new StateImpl();
        S21 = new StateImpl(true, TokenEnum.BOOLEAN, "S21");
        getS1().addTransition(new TransitionImpl('#', S20));
        S20.addTransition(new TransitionImpl('t', S21));
        S20.addTransition(new TransitionImpl('f', S21));
    }

    @Test
    void testTrueLiteral() {
        characters.add('#');
        characters.add('t');
        characters.add(' ');

        assertTestCase("'#t' should be a valid BOOL.");
    }

    @Test
    void testFalseLiteral() {
        characters.add('#');
        characters.add('f');
        characters.add(' ');

        assertTestCase("'#f' should be a valid BOOL.");
    }

    @Override
    public void assertTestCase(String message) {
        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(1, dfa.getAcceptedTokens().size(), "There should be 1 accepted token.");
        assertEquals(TokenEnum.BOOLEAN, dfa.getAcceptedTokens().get(0).getType(), message);
    }

}
