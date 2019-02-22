package com.superscan;

import com.superscan.enums.Tokens;
import com.superscan.scanners.DFA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScannerController {

    private DFA dfa = new DFA();
    private ArrayList<Character> chars = new ArrayList<>();
    private Integer lineIndex;

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
        lineIndex = 1;
    }

    private void handleValidTokens() {
        for (Token token : dfa.getAcceptedTokens()) {
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
        Tokens tokenType = Tokens.INDETERMINATE;

        for (Character c : chars) {
            tokenType = dfa.transitionFunction(c);
            if (tokenType.equals(Tokens.INVALID))
                if (handleInvalidToken()) return;
        }
        if (!tokenIsAccepted(tokenType)) handleInvalidToken();
        else handleValidTokens();
    }

}
