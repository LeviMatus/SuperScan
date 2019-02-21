package com.superscan;

import com.superscan.utils.CharUtils;

public class Main {

    public static void main(String[] args) {
        ScannerController controller = new ScannerController("source.ss");
        controller.analyzeFile();
    }
}