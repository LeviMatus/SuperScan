package com.superscan.scanners;

public class StringScanner implements ScannerInterface {

    //TODO these enums should be incorporated into separate enums for the project.
    enum States {
        STATE_1, STATE_2, STATE_3, STATE_4, STATE_5, STATE_6, STATE_7, STATE_8, STATE_9, STATE_10, STATE_11, STATE_12,
        STATE_13, STATE_14, STATE_15, STATE_16, STATE_17, STATE_18, STATE_19, STATE_20, STATE_21, STATE_22;
    }

    enum Tokens {
        STRING, INVALID, INDETERMINATE
    }

    private String token;
    private int start, stop;
    private States currentState;

    public StringScanner(){}
    
    @Override
    public void resetStates(String token) {
        this.currentState = States.STATE_1;
        this.token = token;
    }
    
    //TODO: Add method for controlling passing a token char by char and maintaining information about indicies
    //THIS METHODS SHOUDL CALL TRANSITIVE FUNCTIN AND HANDLE INVALID STATES RETURNED BY IT.

    /**
     * Iterate over all characters in a token string.
     * If any state transition is invalid, then reject the token. Otherwise, if upon processing the last character,
     * we find ourselves in the end state, we succeed. Return the class' token. Otherwise, return INVALID.
     *
     * @param token A String to be scanned
     * @return a Tokens enum entry which reflects the language's acceptance of the token.
     */
    public Tokens driver(String token) {
        Tokens result;
        for (Character curr : token.toCharArray()) {
            result = this.transitionFunction(curr);
            if (result.equals(Tokens.INVALID)) return result;
        }
        if (currentState.equals(States.STATE_3)) return Tokens.STRING;
        return Tokens.INVALID;
    }
    
    @Override
    public Tokens transitionFunction(Character curr) {

        switch (this.currentState) {
            case STATE_1:
                if (curr.equals('"')) currentState = States.STATE_2;
                else return Tokens.INVALID;
                break;

            case STATE_2:
                if (curr.equals('"')) currentState = States.STATE_3;
                else if (curr.equals('[')) currentState = States.STATE_4;
                else if (curr.equals('\\')) currentState = States.STATE_5;
                else return Tokens.INVALID;
                break;
                
            case STATE_3:
                return Tokens.INVALID;

            case STATE_4:
                if (curr.equals('^')) currentState = States.STATE_6;
                else return Tokens.INVALID;
                break;

            case STATE_5:
                if (curr.equals('\\')) currentState = States.STATE_7;
                else return Tokens.INVALID;
                break;

            case STATE_6:
                if (curr.equals('\\')) currentState = States.STATE_8;
                else return Tokens.INVALID;
                break;

            case STATE_7:
                if (curr.equals('[')) currentState = States.STATE_9;
                else return Tokens.INVALID;
                break;

            case STATE_8:
                if (curr.equals('"')) currentState = States.STATE_10;
                else return Tokens.INVALID;
                break;

            case STATE_9:
                if (curr.equals('0')) currentState = States.STATE_11;
                else if (curr.equals('t')) currentState = States.STATE_12;
                else return Tokens.INVALID;
                break;

            case STATE_10:
                if (curr.equals(']')) currentState = States.STATE_2;
                else return Tokens.INVALID;
                break;

            case STATE_11:
                if (curr.equals('-')) currentState = States.STATE_13;
                else return Tokens.INVALID;
                break;

            case STATE_12:
                if (curr.equals('n')) currentState = States.STATE_10;
                else return Tokens.INVALID;
                break;

            case STATE_13:
                if (curr.equals('3')) currentState = States.STATE_14;
                else return Tokens.INVALID;
                break;

            case STATE_14:
                if (curr.equals(']')) currentState = States.STATE_15;
                else return Tokens.INVALID;
                break;

            case STATE_15:
                if (curr.equals('[')) currentState = States.STATE_16;
                else return Tokens.INVALID;
                break;

            case STATE_16:
                if (curr.equals('0')) currentState = States.STATE_17;
                else return Tokens.INVALID;
                break;

            case STATE_17:
                if (curr.equals('-')) currentState = States.STATE_18;
                else return Tokens.INVALID;
                break;

            case STATE_18:
                if (curr.equals('7')) currentState = States.STATE_19;
                else return Tokens.INVALID;
                break;

            case STATE_19:
                if (curr.equals(']')) currentState = States.STATE_20;
                else return Tokens.INVALID;
                break;

            case STATE_20:
                if (curr.equals('{')) currentState = States.STATE_21;
                else return Tokens.INVALID;
                break;

            case STATE_21:
                if (curr.equals('2')) currentState = States.STATE_22;
                else return Tokens.INVALID;
                break;

            case STATE_22:
                if (curr.equals('}')) currentState = States.STATE_2;
                else return Tokens.INVALID;
                break;
        }

        return Tokens.INDETERMINATE;
    }

}
