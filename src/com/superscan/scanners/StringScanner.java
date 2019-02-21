package com.superscan.scanners;

import com.superscan.enums.States;
import com.superscan.enums.Tokens;
import com.superscan.utils.CharUtils;

public class StringScanner extends TokenScanner {

    public StringScanner() {
        this.goalState = States.STATE_3;
        this.tokenType = Tokens.STRING;
    }

    public Integer errorLocation() {
        return this.getStart() + this.getPos();
    }
    
    @Override
    protected Tokens transitionFunction(Character curr) {

        int lowerBound;
        int upperBound;
        String octalString;

        switch (this.currentState) {
            case STATE_1:
                if (curr.equals('"')) currentState = States.STATE_2;
                else return Tokens.INVALID;
                break;

            case STATE_2:
                if (curr.equals('"')) currentState = States.STATE_3;
                else if (curr.equals('\\')) currentState = States.STATE_4;
                else if (CharUtils.charToASCII(curr) <= 255) return Tokens.INDETERMINATE;
                else return Tokens.INVALID;
                break;

            case STATE_3:
                return Tokens.INVALID;

            case STATE_4:
                lowerBound = 0;
                upperBound = 3;
                octalString = CharUtils.charToOctal(curr);
                if (curr.equals('n') || curr.equals('t')) currentState = States.STATE_2;
                else if ( octalString.charAt(0) >= lowerBound && octalString.charAt(0) <= upperBound )  //lowerbound may be logically redundant. can have char of neg value
                    currentState = States.STATE_5;
                else return Tokens.INVALID;
                break;

            case STATE_5:
                lowerBound = 0;
                upperBound = 7;
                octalString = CharUtils.charToOctal(curr);
                if ( octalString.charAt(0) >= lowerBound && octalString.charAt(0) <= upperBound )  //lowerbound may be logically redundant. can have char of neg value
                    currentState = States.STATE_6;
                else return Tokens.INVALID;
                break;

            case STATE_6:
                lowerBound = 0;
                upperBound = 7;
                octalString = CharUtils.charToOctal(curr);
                if ( octalString.charAt(0) >= lowerBound && octalString.charAt(0) <= upperBound )  //lowerbound may be logically redundant. can have char of neg value
                    currentState = States.STATE_2;
                else return Tokens.INVALID;
                break;
        }

        return Tokens.INDETERMINATE;
    }

}
