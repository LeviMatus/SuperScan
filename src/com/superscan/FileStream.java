package com.superscan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class FileStream {

    private File file;
    private Scanner scanner;
    private LinkedList<ArrayList<Character>> fileLines = new LinkedList<>();

    private Integer curr_node;
    private Integer curr_char;

    private boolean fileExhausted;

    public FileStream(String fName) {
        file = new File(fName);
        fileExhausted = false;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("The provided file was not found. Terminating.");
            System.exit(-1);
        }
        populateNewLine();
    }

    private void populateNewLine() {
        String line = null;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            fileExhausted = true;
            return;
        }
        ArrayList<Character> chars = new ArrayList<>();
        for (Character character : line.toCharArray()) {
            chars.add(character);
        }
        fileLines.add(chars);
        curr_char = 0;
        if (curr_node == null)
            curr_node = 0;
        else
            curr_node++;
    }

    private Character fetchCharacter() {
        Character next = fileLines.get(curr_node).get(curr_char);
        curr_char++;
        return next;
    }

    public Character getChar() {
        try {
            return fetchCharacter();
        } catch (IndexOutOfBoundsException e) {
            populateNewLine();
            if (!fileExhausted)
                return fetchCharacter();
            return null;
        }
    }
}
