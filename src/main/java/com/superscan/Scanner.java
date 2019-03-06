package com.superscan;

public class Scanner {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the relative path of the file. Exiting.");
            System.exit(-1);
        }
        ScannerController controller = new ScannerController(args[0]);
        controller.analyzeFile();
    }
}