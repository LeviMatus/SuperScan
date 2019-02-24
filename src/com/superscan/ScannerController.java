package com.superscan;

import com.superscan.dfa.FSMImpl;
import com.superscan.enums.Tokens;
import com.superscan.scanners.DFA;
import com.superscan.states.State;
import com.superscan.states.StateImpl;
import com.superscan.transitions.InvalidTokenException;
import com.superscan.transitions.TransitionImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScannerController {

    private DFA dfa = new DFA();
    private FSMImpl fsm;
    private ArrayList<Character> chars = new ArrayList<>();

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

        // States to belong to FSM
        State S1 = new StateImpl();
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

        // Initialize State Transitions
        S1 = S1.addTransition(new TransitionImpl('0', S11));
        S1 = S1.addTransition(new TransitionImpl('-', S9));
        S1 = S1.addTransition(new TransitionImpl('+', S9));
        S3 = S3.addTransition(new TransitionImpl('-', S6));
        S3 = S3.addTransition(new TransitionImpl('+', S6));
        S9 = S9.addTransition(new TransitionImpl('.', S13));
        S11 = S11.addTransition(new TransitionImpl('b', S2));
        S11 = S11.addTransition(new TransitionImpl('x', S4));
        S11 = S11.addTransition(new TransitionImpl('.', S13));

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

        fsm = new FSMImpl(S1);
    }

    private State addUpperAndLowercase(State state, State next, int i) {
        state = state.addTransition(new TransitionImpl((char) ('a' + i), next));
        return state.addTransition(new TransitionImpl((char) ('A' + i), next));
    }

    private void handleValidTokens() {
        for (Token token : fsm.getTokens()) {
            System.out.println(token.toString());
        }
    }

    private boolean handleInvalidToken() {
        Token token = dfa.getCurrToken();
        if (dfa.currentTokenIndex().equals(dfa.getStart())) {
            Character singleCharToken = token.getVal().charAt(0);
            token.setVal(singleCharToken.toString());
        }
        /**
         * treat single-len errors as single-char tokens. Otherwise, it's multi-char.
         */
        if (dfa.getCurrToken().getVal().length() > 1 && !dfa.isAborting()) {
            dfa.abort();
            return false;
        }
        System.out.println(token);
        return true;
    }

    private boolean tokenIsAccepted(Tokens token) {
        return !token.equals(Tokens.INDETERMINATE) && !token.equals(Tokens.INVALID);
    }

    public void analyzeFile() {

        for (Character c : chars) {
            try {
                fsm.transition(c);
            } catch (InvalidTokenException e) {
                System.out.println("Well, shit!");
            }
        }

        if (!fsm.isSatisfied()) handleInvalidToken();
        else handleValidTokens();
    }

}
