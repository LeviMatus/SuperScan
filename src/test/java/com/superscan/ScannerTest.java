package com.superscan;

import com.superscan.enums.Tokens;
import com.superscan.states.State;
import com.superscan.states.StateImpl;
import com.superscan.transitions.TransitionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScannerTest extends AbstractScannerClass<Integer> {

    private State S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13;

    @BeforeEach
    void constructStates() {
        S2 = new StateImpl();
        S3 = new StateImpl();
        S4 = new StateImpl();
        S5 = new StateImpl(true, Tokens.NUMBER);
        S6 = new StateImpl();
        S7 = new StateImpl(true, Tokens.NUMBER);
        S8 = new StateImpl(true, Tokens.NUMBER);
        S9 = new StateImpl();
        S10 = new StateImpl(true, Tokens.NUMBER);
        S11 = new StateImpl(true, Tokens.NUMBER);
        S12 = new StateImpl(true, Tokens.NUMBER);
        S13 = new StateImpl();

        this.getS1().addTransition(new TransitionImpl('0', S11));
        this.getS1().addTransition(new TransitionImpl('-', S9));
        this.getS1().addTransition(new TransitionImpl('+', S9));
        this.getS1().addTransition(new TransitionImpl('.', S13));
        S3.addTransition(new TransitionImpl('-', S6));
        S3.addTransition(new TransitionImpl('+', S6));
        S9.addTransition(new TransitionImpl('.', S13));
        S11.addTransition(new TransitionImpl('b', S2));
        S11.addTransition(new TransitionImpl('x', S4));
        S11.addTransition(new TransitionImpl('.', S13));
        S12.addTransition(new TransitionImpl('.', S13));


        for (int i = 0; i < 26; i++) {
            if (i < 10) { // Numbered transitions
                Character digit = Character.forDigit(i, 10);
                if (i == 0 || i == 1) { // binary number transitions;
                    S2 = S2.addTransition(new TransitionImpl(digit, S8));
                    S8 = S8.addTransition(new TransitionImpl(digit, S8));
                }
                if (i > 0) S1 = S1.addTransition(new TransitionImpl(digit, S12));

                S3 = S3.addTransition(new TransitionImpl(digit, S7));
                S4 = S4.addTransition(new TransitionImpl(digit, S10));
                S5 = S5.addTransition(new TransitionImpl(digit, S5));
                S6 = S6.addTransition(new TransitionImpl(digit, S7));
                S7 = S7.addTransition(new TransitionImpl(digit, S7));
                S9 = S9.addTransition(new TransitionImpl(digit, S12));
                S10 = S10.addTransition(new TransitionImpl(digit, S10));
                S11 = S11.addTransition(new TransitionImpl(digit, S12));
                S12 = S12.addTransition(new TransitionImpl(digit, S12));
                S13 = S13.addTransition(new TransitionImpl(digit, S5));
            }

            if ('e' == ('a' + i)) {
                S11 = addUpperAndLowercase(S11, S3, i);
                S12 = addUpperAndLowercase(S12, S3, i);
                S5 = addUpperAndLowercase(S5, S3, i);
            }

            S4 = addUpperAndLowercase(S4, S10, i);
            S10 = addUpperAndLowercase(S10, S10, i);
        }
    }

    private State addUpperAndLowercase(State state, State next, int i) {
        state = state.addTransition(new TransitionImpl((char) ('a' + i), next));
        return state.addTransition(new TransitionImpl((char) ('A' + i), next));
    }

    @Test
    void testIntegerTokens() {
        characters.add('1');
        characters.add('3');
        characters.add(' ');

        assertTestCase("13 should be an accepted token.");
    }

    @Test
    void testBinaryNumber() {
        characters.add('0');
        characters.add('b');
        characters.add('1');
        characters.add('0');
        characters.add(' ');

        assertTestCase("0b10 should be an accepted token.");
    }

    @Test
    void testHexadecimalNumber() {
        characters.add('0');
        characters.add('x');
        characters.add('1');
        characters.add('a');
        characters.add('F');
        characters.add(' ');

        assertTestCase("0x1aF should be an accepted token.");
    }

    @Test
    void testDecimalNumber() {
        characters.add('.');
        characters.add('1');
        characters.add(' ');

        assertTestCase(".1 should be an accepted token.");
        setup();
        constructStates();
        characters.add('8');
        characters.add('.');
        characters.add('1');
        characters.add('e');
        characters.add('2');
        characters.add(' ');

        assertTestCase("8.1e2 should be an accepted token.");
    }

    @Test
    void testSignedNumber() {
        characters.add('+');
        characters.add('1');
        characters.add(' ');

        assertTestCase("+1 should be an accepted token.");
        setup();
        constructStates();
        characters.add('+');
        characters.add('.');
        characters.add('1');
        characters.add(' ');

        assertTestCase("+.1 should be an accepted token.");
    }


    @Override
    void assertTestCase(String message) {
        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(1, dfa.getAcceptedTokens().size(), "There should be 1 accepted token.");
        assertEquals(Tokens.NUMBER, dfa.getAcceptedTokens().get(0).getType(), message);
    }
}
