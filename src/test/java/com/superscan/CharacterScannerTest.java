package com.superscan;

import com.superscan.enums.TokenEnum;
import com.superscan.states.State;
import com.superscan.states.StateImpl;
import com.superscan.transitions.TransitionImpl;
import com.superscan.utils.CharUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterScannerTest extends AbstractScannerTest<Character> {

    private static State S20, S22, S23, S24, S25, S26, S27, S28, S29, S30, S31, S32, S33, S34, S35, S36;

    @BeforeAll
    static void constructStates() {
        State S20 = new StateImpl();
        State S22 = new StateImpl();
        State S23 = new StateImpl(true, TokenEnum.CHAR, "S23");
        State S24 = new StateImpl(true, TokenEnum.CHAR, "S24");
        State S25 = new StateImpl();
        State S26 = new StateImpl(true, TokenEnum.CHAR, "S26");
        State S27 = new StateImpl();
        State S28 = new StateImpl(true, TokenEnum.CHAR, "S28");
        State S29 = new StateImpl();
        State S30 = new StateImpl();
        State S31 = new StateImpl();
        State S32 = new StateImpl(true, TokenEnum.CHAR, "S32");
        State S33 = new StateImpl();
        State S34 = new StateImpl();
        State S35 = new StateImpl();
        State S36 = new StateImpl();

        AbstractScannerTest.getS1().addTransition(new TransitionImpl('#', S20));

        S20.addTransition(new TransitionImpl('\\', S22));
        S22.addTransition(new TransitionImpl(S23, 't', 's', 'n', '0', '1', '2', '3'));
        S22.addTransition(new TransitionImpl('t', S26));
        S22.addTransition(new TransitionImpl('s', S28));
        S22.addTransition(new TransitionImpl('n', S32));
        S26.addTransition(new TransitionImpl('a', S27));
        S27.addTransition(new TransitionImpl('b', S23));
        S28.addTransition(new TransitionImpl('p', S29));
        S29.addTransition(new TransitionImpl('a', S30));
        S30.addTransition(new TransitionImpl('c', S31));
        S31.addTransition(new TransitionImpl('e', S23));
        S32.addTransition(new TransitionImpl('e', S33));
        S33.addTransition(new TransitionImpl('w', S34));
        S34.addTransition(new TransitionImpl('l', S35));
        S35.addTransition(new TransitionImpl('i', S36));
        S36.addTransition(new TransitionImpl('n', S31));


        for (int i = 0; i < 26; i++) {
            if (i < 10) { // Numbered transitions
                Character digit = Character.forDigit(i, 10);
                if (i <= 3) {
                    S22.addTransition(new TransitionImpl(digit, S24));
                }
                if (CharUtils.charInRange(digit, 0, 7)) {
                    S24.addTransition(new TransitionImpl(digit, S25));
                    S25.addTransition(new TransitionImpl(digit, S23));
                }
            }
        }
    }

    @Test
    void testTabCharLiteral() {
        characters.add('#');
        characters.add('\\');
        characters.add('t');
        characters.add('a');
        characters.add('b');
        characters.add(' ');

        assertTestCase("'#\tab' should be an accepted token.");
    }

    @Test
    void testSpaceCharLiteral() {
        characters.add('#');
        characters.add('\\');
        characters.add('s');
        characters.add('p');
        characters.add('a');
        characters.add('c');
        characters.add('e');
        characters.add(' ');

        assertTestCase("'#\\space' should be an accepted token.");
    }

    @Test
    void testNewlineCharLiteral() {
        characters.add('#');
        characters.add('\\');
        characters.add('n');
        characters.add('e');
        characters.add('w');
        characters.add('l');
        characters.add('i');
        characters.add('n');
        characters.add('e');
        characters.add(' ');

        assertTestCase("'#\newline' should be an accepted token.");
    }

    @Test
    void testOctalCharLiteral() {
        characters.add('#');
        characters.add('\\');
        characters.add('0');
        characters.add('3');
        characters.add('7');
        characters.add(' ');

        assertTestCase("'#\n031' should be an accepted token.");
    }

    @Test
    void testOctalOutOfRange() {
        characters.add('#');
        characters.add('\\');
        characters.add('4');
        characters.add('3');
        characters.add('3');
        characters.add(' ');
        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(0, dfa.getAcceptedTokens().size(), "There should be 0 accepted tokens.");
        reset();

        characters.add('#');
        characters.add('\\');
        characters.add('1');
        characters.add('8');
        characters.add('3');
        characters.add(' ');
        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(0, dfa.getAcceptedTokens().size(), "There should be 0 accepted tokens.");
        reset();

        characters.add('#');
        characters.add('\\');
        characters.add('1');
        characters.add('3');
        characters.add('8');
        characters.add(' ');
        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(0, dfa.getAcceptedTokens().size(), "There should be 0 accepted tokens.");
    }

    @Test
    void testTwoNonKeyCharacters() {
        characters.add('#');
        characters.add('\\');
        characters.add('a');
        characters.add('b');
        characters.add(' ');

        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(0, dfa.getAcceptedTokens().size(), "There should be 0 accepted tokens.");
    }

    @Override
    void assertTestCase(String message) {
        scannerController.setChars(characters);
        scannerController.analyzeFile();
        assertEquals(1, dfa.getAcceptedTokens().size(), "There should be 1 accepted token.");
        assertEquals(TokenEnum.CHAR, dfa.getAcceptedTokens().get(0).getType(), message);
    }
}
