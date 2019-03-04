package com.superscan;

import com.superscan.dfa.DFA;
import com.superscan.dfa.DFAImpl;
import com.superscan.states.InitialStateImpl;
import com.superscan.states.State;
import com.superscan.transitions.TransitionImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public abstract class AbstractScannerTest<T> {

    DFA dfa;
    static State S1;
    ArrayList<Character> characters;
    ScannerController scannerController;

    @BeforeAll
    static void setup() {
        S1 = new InitialStateImpl();
    }

    @BeforeEach
    void reset() {
        dfa = new DFAImpl(S1);
        scannerController = new ScannerController(dfa);

        characters = new ArrayList<>();
    }

    static State getS1() {
        return S1;
    }

    public void addRanges(State root, State child, int start, int stop) {
        assert(start < stop);
        for (int i = start; i <= stop; i++) {
            Character digit = Character.forDigit(i, 10);
            root.addTransition(new TransitionImpl(digit, child));
        }
    }

    abstract void assertTestCase(String message);

}
