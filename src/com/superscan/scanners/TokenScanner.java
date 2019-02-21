package com.superscan.scanners;

import com.superscan.enums.States;
import com.superscan.enums.Tokens;

import java.util.ArrayList;

abstract public class TokenScanner implements ScannerInterface {

    // Starting position in line for char and current position in token.
    private Integer start, pos;
    private String rejectedToken;
    private boolean rejected;

    States currentState;
    States goalState;
    Tokens tokenType;

    /**
     * To be called before every scan. Ensure we start from scratch
     * and have a clean slate for the new token.
     *
     * @param start Index of where the token starts in the line.
     */
    public void setup(Integer start) {
        this.currentState = States.STATE_1;
        this.pos = 0;
        this.start = start;
        this.rejected = false;
    }

    public Integer getStart() {return this.start;}
    public Integer getPos() {return this.pos;}
    public String getRejectedToken() {return this.rejectedToken;}
    public boolean hasRejected() {return this.rejected;}

    /**
     * If any state transition is invalid, then reject the token. Otherwise, if upon processing the last character,
     * we find ourselves in the end state, we succeed. Return the class' token. Otherwise, return INVALID.
     *
     * @param chars A char to be evaluated.
     * @return a Tokens enum entry which reflects the language's acceptance of the token.
     */
    public Tokens scan(ArrayList<Character> chars) { //TODO update java doc
        Tokens result = Tokens.INVALID;

        for (Character character : chars) {
            result = transitionFunction(character);
            this.pos++;
            if (result.equals(Tokens.INVALID)) {
                this.rejected = true;
                return result;
            }
            if (currentState.equals(this.goalState)) return this.tokenType;
        }
        return result;
    }

    /**
     * A transition function function which mimics the state changing logic of a DFA.
     * This must be implemented by the developer.
     *
     * @param curr The current character which to scan and evaluate
     * @return Tokens enum type. INVALID if a unsupported State transition is
     * attempted. INDETERMINATE if the decision has yet to be made. This method
     * should not accept a state, simply transition. The calling method, {@link #scan(ArrayList<Character>)},
     * will ultimately accept a token or not.
     */
    abstract protected Tokens transitionFunction(Character curr);
}
