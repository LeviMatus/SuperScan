package com.superscan;

public class ScannerController {

    private FileStream fileStream;

    public ScannerController(String fName) {
        fileStream = new FileStream(fName);
    }

    public void analyzeFile() {

        while (!fileStream.isFileExhausted()) {
            Character curr = fileStream.getChar();

            //TODO: Call DFA Scanners in a safe order.


        }

    }

}
