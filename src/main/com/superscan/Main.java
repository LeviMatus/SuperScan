package main.com.superscan;

public class Main {

    public static void main(String[] args) {
        ScannerController controller = new ScannerController("source.ss");
        controller.analyzeFile();
    }
}