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
import com.superscan.enums.Tokens;
import com.superscan.states.CommentStateImpl;
import com.superscan.states.InitialStateImpl;
import com.superscan.states.State;
import com.superscan.states.StateImpl;
import com.superscan.dfa.InvalidTokenException;
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
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Main.class.getResource(fName).getFile())))) {
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
        State S2 = new StateImpl();
        State S3 = new StateImpl();
        State S4 = new StateImpl();
        State S5 = new StateImpl(true, Tokens.NUMBER);
        State S6 = new StateImpl();
        State S7 = new StateImpl(true, Tokens.NUMBER);
        State S8 = new StateImpl(true, Tokens.NUMBER);
        State S9 = new StateImpl();
        State S10 = new StateImpl(true, Tokens.NUMBER);
        State S11 = new StateImpl(true, Tokens.NUMBER);
        State S12 = new StateImpl(true, Tokens.NUMBER);
        State S13 = new StateImpl();
        State S14 = new StateImpl(false, "S14");
        State S15 = new StateImpl(true, Tokens.STRING, "S15");
        State S16 = new StateImpl(false, S14, "S16");
        State S18 = new StateImpl(false, S14, "S18");
        State S19 = new StateImpl(false, S14, "S19");

        State S42 = new CommentStateImpl();

        // Initialize State Transitions
        S1 = S1.addTransition(new TransitionImpl('0', S11));
        S1 = S1.addTransition(new TransitionImpl('-', S9));
        S1 = S1.addTransition(new TransitionImpl('+', S9));
        S1 = S1.addTransition(new TransitionImpl('.', S13));
        S1 = S1.addTransition(new TransitionImpl('"', S14));
        S1 = S1.addTransition(new TransitionImpl(';', S42));
        S3 = S3.addTransition(new TransitionImpl('-', S6));
        S3 = S3.addTransition(new TransitionImpl('+', S6));
        S9 = S9.addTransition(new TransitionImpl('.', S13));
        S11 = S11.addTransition(new TransitionImpl('b', S2));
        S11 = S11.addTransition(new TransitionImpl('x', S4));
        S11 = S11.addTransition(new TransitionImpl('.', S13));
        S12 = S12.addTransition(new TransitionImpl('.', S13));
        S14 = S14.addTransition(new TransitionImpl(S14, '"', '\\'));
        S14 = S14.addTransition(new TransitionImpl('"', S15));
        S14 = S14.addTransition(new TransitionImpl('\\', S16));
        S16 = S16.addTransition(new TransitionImpl('n', S14));
        S16 = S16.addTransition(new TransitionImpl('t', S14));

        S42 = S42.addTransition(new TransitionImpl('\n', S1));
        S42 = S42.addTransition(new TransitionImpl(S42, '\n'));

        for (int i = 0; i < 26; i++) {
            if (i < 10) { // Numbered transitions
                Character digit = Character.forDigit(i, 10);
                if (i == 0 || i == 1) { // binary number transitions;
                    S2 = S2.addTransition(new TransitionImpl(digit, S8));
                    S8 = S8.addTransition(new TransitionImpl(digit, S8));
                }
                if (i > 0) S1 = S1.addTransition(new TransitionImpl(digit, S12));
                if (i <= 3) S16 = S16.addTransition(new TransitionImpl(digit, S18));
                if (CharUtils.charInRange(digit, 1, 7)) {
                    S18 = S18.addTransition(new TransitionImpl(digit, S19));
                    S19 = S19.addTransition(new TransitionImpl(digit, S14));
                }
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

        fsm = new DFAImpl(S1);
    }

    public void setChars(ArrayList<Character> chars) {
        this.chars = chars;
    }

    private State addUpperAndLowercase(State state, State next, int i) {
        state = state.addTransition(new TransitionImpl((char) ('a' + i), next));
        return state.addTransition(new TransitionImpl((char) ('A' + i), next));
    }

    private void handleValidTokens() {
        for (Token token : fsm.getAcceptedTokens()) {
            System.out.println(token.toString());
        }
    }

    public void analyzeFile() {

        for (Character c : chars) {
            try {
                fsm = fsm.transition(c);
            } catch (InvalidTokenException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }

        handleValidTokens();
    }

}
