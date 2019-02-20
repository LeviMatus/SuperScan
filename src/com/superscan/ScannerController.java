package com.superscan;

import com.superscan.enums.Tokens;
import com.superscan.scanners.StringScanner;
import com.superscan.scanners.TokenScanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScannerController {

    private StringScanner stringScanner = new StringScanner();
    private ArrayList<Character> chars = new ArrayList<>();
    private Integer lineNo;

    public ScannerController(String fName) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Main.class.getResource(fName).getFile())))) {
            while (br.ready()) {
                Character[] lineChars = br.readLine().chars().mapToObj(c -> (char)c).toArray(Character[]::new);
                chars.addAll(Arrays.asList(lineChars));
            }
        } catch (IOException e) {
            System.out.println("An error occurred. Ensure you supplied the correct filename and/or path.");
            System.exit(-1);
        }
        stringScanner.setup(0);
        lineNo = 0;
    }

    public void handleValidToken(Tokens token, TokenScanner scanner) {
        String msg = String.format("%s %d:%d", token.toString(), lineNo, scanner.getStart());
        System.out.println(msg);

        //TODO: Last thing here should be to reset scanners.
        Integer newStart = scanner.getPos() + scanner.getStart();
        stringScanner.setup(newStart);
    }

    public void handleInvalidToken(Tokens token, TokenScanner scanner) {
        //TODO: Implement this.
    }

    public boolean tokenIsAccepted(Tokens token) {
        return !token.equals(Tokens.INDETERMINATE) && !token.equals(Tokens.INVALID);
    }

    public void analyzeFile() {

        Tokens result = Tokens.INDETERMINATE;
        for (Character c : chars) {

            do {
                result = stringScanner.scan(c);
                if (tokenIsAccepted(result)) handleValidToken(result, stringScanner);
            } while (!stringScanner.hasRejected());

        }

    }

    private boolean scannersExhausted() {
        return stringScanner.hasRejected();
    }

}
