package com.superscan;

public class Main {

    public static void main(String[] args) {
	// write your code here

        FileStream stream = new FileStream("/home/levi/IdeaProjects/SuperScan/src/com/superscan/source.ss");
        Character myChar;
        do {
            myChar = stream.getChar();
            System.out.println(myChar);
        } while (myChar != null);

    }
}
