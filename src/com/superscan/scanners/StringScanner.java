package com.superscan.scanners;

public class StringScanner extends TokenScanner {

    //TODO these enums should be incorporated into separate enums for the project.
    enum States {
        STATE_1, STATE_2, STATE_3, STATE_4, STATE_5, STATE_6, STATE_7, STATE_8, STATE_9, STATE_10, STATE_11, STATE_12,
        STATE_13, STATE_14, STATE_15, STATE_16, STATE_17, STATE_18, STATE_19, STATE_20, STATE_21, STATE_22;
    }

    enum Tokens {
        STRING, INVALID, INDETERMINATE
    }

    public StringScanner() {
        this.goalState = States.STATE_3;
        this.tokenType = Tokens.STRING;
    }

    public Integer errorLocation() {
        return this.getStart() + this.getPos();
    }
    
    @Override
    protected Tokens transitionFunction(Character curr) {

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
