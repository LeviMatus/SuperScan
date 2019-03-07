/**
 * References
 *
 * https://www.baeldung.com/java-finite-automata
 * https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous-1/src/main/java/com/baeldung/algorithms/automata
 *
 * Baeldung features a tutorial on how to setup a design pattern for validating input with a
 * Finite State Machine. The source code from the tutorial is also referenced above.
 *
 * We decided to use this design pattern because it seemed more portable than using a unwieldy switch statement. FSM's
 * use are not limited to compiler frontend design, and finding a more modular way of working with the components
 * involved with them may be of good experience should we find an application for them in the future.
 *
 * Our controller, states, DFA, and transitions are all based on the code from above. However, the Baeldung
 * implementation is somewhat limited. It is for a general state-change pattern. It does not broach the topic of
 * tokenizing or labeling tokens.
 */

package com.superscan;

import com.superscan.dfa.DFA;
import com.superscan.dfa.DFAImpl;
import com.superscan.dfa.InvalidTokenException;
import com.superscan.enums.TokenEnum;
import com.superscan.states.CommentStateImpl;
import com.superscan.states.InitialStateImpl;
import com.superscan.states.State;
import com.superscan.states.StateImpl;
import com.superscan.transitions.TransitionImpl;
import com.superscan.utils.CharUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScannerController {

    private DFA fsm;
    private ArrayList<Character> chars = new ArrayList<>();

    public ScannerController(DFA dfa) {this.fsm = dfa;}

    public ScannerController(String fName) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fName)))) {
            while (br.ready()) {
                Character[] lineChars = br.readLine().chars().mapToObj(c -> (char)c).toArray(Character[]::new);
                chars.addAll(Arrays.asList(lineChars));
                chars.add('\n');
            }
        } catch (IOException e) {
            System.out.println("An error occurred. Ensure you supplied the correct filename and/or path.");
            System.exit(-1);
        }

        // States to belong to DFA
        State S1 = new InitialStateImpl();

        State S38 = new StateImpl(true, TokenEnum.SINGLE_QOUTE);
        State S39 = new StateImpl(true, TokenEnum.OPENRD);
        State S40 = new StateImpl(true, TokenEnum.CLOSERD);
        State S41 = new StateImpl(true, TokenEnum.OPENSQ);
        State S43 = new StateImpl(true, TokenEnum.CLOSESQ);
        State S44 = new StateImpl(true, TokenEnum.OPENCU);
        State S45 = new StateImpl(true, TokenEnum.CLOSECU);

        State S2 = new StateImpl();
        State S3 = new StateImpl();
        State S4 = new StateImpl();
        State S5 = new StateImpl(true, TokenEnum.NUMBER);
        State S6 = new StateImpl();
        State S7 = new StateImpl(true, TokenEnum.NUMBER);
        State S8 = new StateImpl(true, TokenEnum.NUMBER);
        State S9 = new StateImpl(true, TokenEnum.IDENTIFIER);
        State S10 = new StateImpl(true, TokenEnum.NUMBER);
        State S11 = new StateImpl(true, TokenEnum.NUMBER);
        State S12 = new StateImpl(true, TokenEnum.NUMBER);
        State S13 = new StateImpl();
        State S14 = new StateImpl(false, "S14");
        State S15 = new StateImpl(true, TokenEnum.STRING, "S15");
        State S16 = new StateImpl(false, S14, "S16");
        State S18 = new StateImpl(false, S14, "S18");
        State S19 = new StateImpl(false, S14, "S19");
        State S20 = new StateImpl();
        State S21 = new StateImpl(true, TokenEnum.BOOLEAN, "S21");
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
        State S42 = new CommentStateImpl();
        State S76 = new StateImpl(true, TokenEnum.IDENTIFIER, "S76");

        // Initialize State Transitions
        S1.addTransition(new TransitionImpl('0', S11));
        S1.addTransition(new TransitionImpl('-', S9));
        S1.addTransition(new TransitionImpl('+', S9));
        S1.addTransition(new TransitionImpl('.', S13));
        S1.addTransition(new TransitionImpl('"', S14));
        S1.addTransition(new TransitionImpl(';', S42));
        S1.addTransition(new TransitionImpl('#', S20));
        S1.addTransition(new TransitionImpl('(', S39));
        S1.addTransition(new TransitionImpl(')', S40));
        S1.addTransition(new TransitionImpl('[', S41));
        S1.addTransition(new TransitionImpl(']', S43));
        S1.addTransition(new TransitionImpl('{', S44));
        S1.addTransition(new TransitionImpl('}', S45));
        S1.addTransition(new TransitionImpl('\'', S38));
        S1.addTransition(new TransitionImpl('=', S76));
        S3.addTransition(new TransitionImpl('-', S6));
        S3.addTransition(new TransitionImpl('+', S6));
        S9.addTransition(new TransitionImpl('.', S13));
        S11.addTransition(new TransitionImpl('b', S2));
        S11.addTransition(new TransitionImpl('x', S4));
        S11.addTransition(new TransitionImpl('.', S13));
        S12.addTransition(new TransitionImpl('.', S13));
        S14.addTransition(new TransitionImpl(S14, '"', '\\'));
        S14.addTransition(new TransitionImpl('"', S15));
        S14.addTransition(new TransitionImpl('\\', S16));
        S16.addTransition(new TransitionImpl('n', S14));
        S16.addTransition(new TransitionImpl('t', S14));
        S20.addTransition(new TransitionImpl('t', S21));
        S20.addTransition(new TransitionImpl('f', S21));
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

        S42.addTransition(new TransitionImpl('\n', S1));
        S42.addTransition(new TransitionImpl(S42, '\n'));

        for (int i = 0; i < 26; i++) {
            if (i < 10) { // Numbered transitions
                Character digit = Character.forDigit(i, 10);
                if (i == 0 || i == 1) { // binary number transitions;
                    S2.addTransition(new TransitionImpl(digit, S8));
                    S8.addTransition(new TransitionImpl(digit, S8));
                }
                if (i > 0) S1.addTransition(new TransitionImpl(digit, S12));
                if (i <= 3) {
                    S16.addTransition(new TransitionImpl(digit, S18));
                    S22.addTransition(new TransitionImpl(digit, S24));
                }
                if (CharUtils.charInRange(digit, 0, 7)) {
                    S18.addTransition(new TransitionImpl(digit, S19));
                    S19.addTransition(new TransitionImpl(digit, S14));
                    S24.addTransition(new TransitionImpl(digit, S25));
                    S25.addTransition(new TransitionImpl(digit, S23));
                }
                S3.addTransition(new TransitionImpl(digit, S7));
                S4.addTransition(new TransitionImpl(digit, S10));
                S5.addTransition(new TransitionImpl(digit, S5));
                S6.addTransition(new TransitionImpl(digit, S7));
                S7.addTransition(new TransitionImpl(digit, S7));
                S9.addTransition(new TransitionImpl(digit, S12));
                S10.addTransition(new TransitionImpl(digit, S10));
                S11.addTransition(new TransitionImpl(digit, S12));
                S12.addTransition(new TransitionImpl(digit, S12));
                S13.addTransition(new TransitionImpl(digit, S5));
                S76.addTransition(new TransitionImpl(digit, S76));
            }

            if ('e' == ('a' + i)) {
                S11 = addUpperAndLowercase(S11, S3, i);
                S12 = addUpperAndLowercase(S12, S3, i);
                S5 = addUpperAndLowercase(S5, S3, i);
            }

            addUpperAndLowercase(S1, S76, i);
            S4 = addUpperAndLowercase(S4, S10, i);
            S10 = addUpperAndLowercase(S10, S10, i);
            addUpperAndLowercase(S76, S76, i);
        }

        fsm = new DFAImpl(S1);
    }

    public void setChars(ArrayList<Character> chars) {
        this.chars = chars;
    }

    private State addUpperAndLowercase(State state, State next, int i) {
        state.addTransition(new TransitionImpl((char) ('a' + i), next));
        state.addTransition(new TransitionImpl((char) ('A' + i), next));
        return state;
    }

    private void handleValidTokens() {
        for (Token token : fsm.getAcceptedTokens()) {
            System.out.println(token.toString());
        }
    }

    public void analyzeFile() {

        for (Character c : chars) {
            try {
                fsm.transition(c);
            } catch (InvalidTokenException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        handleValidTokens();
    }

}
