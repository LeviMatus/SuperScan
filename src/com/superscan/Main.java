package com.superscan;

public class Main {

    public static void main(String[] args) {

        FileStream stream = new FileStream("source.ss");
        Character myChar;
        do {
            myChar = stream.getChar();
            System.out.println(myChar);
        } while (myChar != null);

    }
}