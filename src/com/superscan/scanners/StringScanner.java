package com.superscan.scanners;

public class StringScanner<T> implements ScannerInterface {

    //TODO these enums should be incorporated into separate enums for the project.
    enum States {
        STATE_1, STATE_2, STATE_3, STATE_4, STATE_5, STATE_6, STATE_7, STATE_8, STATE_9, STATE_10, STATE_11, STATE_12,
        STATE_13, STATE_14, STATE_15, STATE_16, STATE_17, STATE_18, STATE_19, STATE_20, STATE_21, STATE_22;
    }

    enum Tokens {
        STRING, INVALID
    }

    private String token;
    private int start, stop;
    private States currentState;
    private Class<T> type;

    /**
     * Create a new Scanner Object. Takes in the class of the Enum it is to return.
     * 
     * @param type Class of an Enum to pass in.
     */
    public StringScanner(Class<T> type) {
        this.type = type;
    }
    
    @Override
    public void resetStates(String token) {
        this.currentState = States.STATE_1;
        this.token = token;
    }
    
    //TODO: Add method for controlling passing a token char by char and maintaining information about indicies
    //THIS METHODS SHOUDL CALL TRANSITIVE FUNCTIN AND HANDLE INVALID STATES RETURNED BY IT.
    
    @Override
    public T tokenType() {
        return type.cast(Tokens.STRING);
    }

    @Override
    public T invalidToken() {
        return type.cast(Tokens.INVALID);
    }
    
    private boolean isCharacterEqual(Character curr, Character requirement) {
        return curr == requirement;
    }

    @Override
    public T transitionFunction(Character curr) {

        switch (this.currentState) {
            case STATE_1:
                if (curr.equals('"')) currentState = States.STATE_2;
                else return invalidToken();
                break;

            case STATE_2:
                if (curr.equals('"')) currentState = States.STATE_3;
                else if (curr.equals('[')) currentState = States.STATE_4;
                else if (curr.equals('\\')) currentState = States.STATE_5;
                else return tokenType();
                break;

            // The goal state. If we are here and there are not more chars to ingest, the finish.
            case STATE_3:
                if (curr == null) return tokenType();
                return invalidToken();

            case STATE_4:
                if (curr == '^') currentState = States.STATE_6;
                else return invalidToken();
                break;

            case STATE_5:
                if (curr == '\\') currentState = States.STATE_7;
                else return invalidToken();
                break;

            case STATE_6:
                if (curr == '\\') currentState = States.STATE_8;
                else return invalidToken();
                break;

            case STATE_7:
                if (curr == '[') currentState = States.STATE_9;
                else return invalidToken();
                break;

            case STATE_8:
                if (curr == '"') currentState = States.STATE_10;
                else return invalidToken();
                break;

            case STATE_9:
                if (curr == '0') currentState = States.STATE_11;
                else if (curr == 't') currentState = States.STATE_12;
                else return invalidToken();
                break;

            case STATE_10:
                if (curr == ']') currentState = States.STATE_2;
                else return invalidToken();
                break;

            case STATE_11:
                if (curr == '-') currentState = States.STATE_13;
                else return invalidToken();
                break;

            case STATE_12:
                if (curr == 'n') currentState = States.STATE_10;
                else return invalidToken();
                break;

            case STATE_13:
                if (curr == '3') currentState = States.STATE_14;
                else return invalidToken();
                break;

            case STATE_14:
                if (curr == ']') currentState = States.STATE_15;
                else return invalidToken();
                break;

            case STATE_15:
                if (curr == '[') currentState = States.STATE_16;
                else return invalidToken();
                break;

            case STATE_16:
                if (curr == '0') currentState = States.STATE_17;
                else return invalidToken();
                break;

            case STATE_17:
                if (curr == '-') currentState = States.STATE_18;
                else return invalidToken();
                break;

            case STATE_18:
                if (curr == '7') currentState = States.STATE_19;
                else return invalidToken();
                break;

            case STATE_19:
                if (curr == ']') currentState = States.STATE_20;
                else return invalidToken();
                break;

            case STATE_20:
                if (curr == '{') currentState = States.STATE_21;
                else return invalidToken();
                break;

            case STATE_21:
                if (curr == '2') currentState = States.STATE_22;
                else return invalidToken();
                break;

            case STATE_22:
                if (curr == '}') currentState = States.STATE_2;
                else return invalidToken();
                break;
        }

        return type.cast(token);
    }

}
