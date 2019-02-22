package com.superscan.scanners;

import com.superscan.Token;
import com.superscan.enums.States;
import com.superscan.enums.Tokens;

import java.util.ArrayList;

public class DFA {

    private States currentState;
    private Integer lineNum, start, offset;
    private Token currToken;
    private boolean aborting;
    private ArrayList<Token> acceptedTokens;

    public DFA() {
        acceptedTokens = new ArrayList<>();
        offset = 0;
        start = lineNum = 1;
        currToken = new Token(start, lineNum);
        aborting = false;
        reset();
    }

    /**
     * begin halt sequence if an invalid token is encountered.
     */
    public void abort() {
        aborting = true;
    }

    public boolean isAborting() {return aborting;}

    public ArrayList<Token> getAcceptedTokens() {
        return this.acceptedTokens;
    }

    public Token getCurrToken() {
        return currToken;
    }

    public Integer getStart() {
        return start;
    }

    /**
     * Returns the index which is currently being scanned.
     * @return index of line where token scanning is taking place
     */
    public Integer currentTokenIndex() {
        return this.start + this.offset;
    }

    /**
     * Set DFA to initial state and increment start, reset offset.
     */
    public void reset() {
        this.currentState = States.STATE_1;
        this.start = this.start + this.offset;
        this.offset = 1;
    }

    /**
     * Finalize an accepted token with a token type.
     */
    public void delimitToken() {
        currToken.setType(currentState.getGoalType());
        acceptedTokens.add(currToken);
        currentState = States.STATE_1;
    }

    public boolean isWhitespace(Character curr) {
        return curr.equals(' ') || curr.equals('\n') || curr.equals('\t');
    }

    private boolean charInRange(Character c, int lower, int upper) {
        return Character.getNumericValue(c) >= lower && Character.getNumericValue(c) <= upper;
    }

    /**
     * newlines initialize a new line, other whitespace chars setup rows and columns for new token.
     * @param c character to evaluate.
     */
    private void handleWhitespace(Character c) {
        if (c.equals('\n')) {
            lineNum++;
            start = 1;
            offset = 1;
        } else reset();
    }

    private boolean isValidToken(Tokens token) {
        return !token.equals(Tokens.INDETERMINATE) && !token.equals(Tokens.INVALID);
    }

    /**
     * Given a token, transition to the next appropriate state. Handles delimiting of accepted tokens
     * when whitespace delimiters are encountered.
     * @param curr Character to scan
     * @return a Tokens enum which indicates the acceptability of the token.
     */
    public Tokens transitionFunction(Character curr) {

        if (isWhitespace(curr)) {
            if (aborting) return Tokens.INVALID;

            if (!currentState.equals(States.STATE_1)) {
                if (isValidToken(currentState.getGoalType())) {
                    delimitToken();
                    handleWhitespace(curr);
                    currToken = new Token(lineNum, start);
                    return acceptedTokens.get(acceptedTokens.size()-1).getType();
                }
                return currToken.getType();
            }
        }

        currToken.addChar(curr);
        offset++;

        if (aborting) return Tokens.INDETERMINATE; // keep scanning if we have a multi-token error.

        switch (currentState) {
            case STATE_1:
                switch (curr) {
                    case ' ':
                    case '\n':
                    case '\t': handleWhitespace(curr); break; // ignore standard whitespace
                    case ';': currentState = States.STATE_42; break; // Comments
                    case '0': currentState = States.STATE_11; break; // Number path for floats and hex/binary
                    case '-':
                    case '+': currentState = States.STATE_9; break; // Number path with + or -
                    case 'e':
                    case 'E': currentState = States.STATE_3; break; // Number path for sci notation
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9': currentState = States.STATE_12; break; // Number path for digits 1-9.
                    case '.': currentState = States.STATE_13; break; // Number starting with a .
                    // Parenthesis Tokens below
                    case '(': currentState = States.STATE_39; break;
                    case ')': currentState = States.STATE_40; break;
                    case '[': currentState = States.STATE_41; break;
                    case ']': currentState = States.STATE_43; break;
                    case '{': currentState = States.STATE_44; break;
                    case '}': currentState = States.STATE_45; break;

                    case '"': currentState = States.STATE_14; break; // String path
                    case '#': currentState = States.STATE_20; break; // Char or boolean path
                    // Identifiers and Keywords
                    case 'q': currentState = States.STATE_46; break; // QUOTE keyword path
                    case 'b': currentState = States.STATE_51; break; // BEGIN keyword path
                    case 'd': currentState = States.STATE_57; break; // DEFINE keyword path
                    case 'i': currentState = States.STATE_62; break; // IF keyword path
                    case 'c': currentState = States.STATE_64; break; // COND keyword path
                    case 'l': currentState = States.STATE_68; break; // LAMBDA or LET keyword path
                    // TODO: Ensure IDENTIFIER transition here makes sense.
                    // Do we need to explicitly transition based on all possible chars here?
                    // We could dismiss non-/377 chars upon entering the parent switch. What about non printable chars?
                    default: currentState = States.STATE_76; break; // Anything leftover must be for an identifier
                }
                break;

            case STATE_2:
            case STATE_3:
                if (curr.equals('+') || curr.equals('-')) currentState = States.STATE_6;
                else if (charInRange(curr, 0, 9)) currentState = States.STATE_7;
                else return Tokens.INVALID;

            case STATE_4:
            case STATE_5:
            case STATE_6:
            case STATE_7:
            case STATE_8:
            case STATE_9:
            case STATE_10:
            case STATE_11:
                if (curr.equals('b')) currentState = States.STATE_2;
                else if (curr.equals('x')) currentState = States.STATE_4;
                else if (charInRange(curr, 0, 9)) currentState = States.STATE_12;
                else if (curr.equals('.')) currentState = States.STATE_13;
                else return Tokens.INVALID;
                break;

            case STATE_12:
                if (charInRange(curr, 0, 9)) break;
                else if (curr.equals('.')) currentState = States.STATE_13;
                else return Tokens.INVALID;
                break;

            case STATE_13:
            case STATE_14:
            case STATE_15:
            case STATE_16:
            case STATE_17:
            case STATE_18:
            case STATE_19:
            case STATE_20:
            case STATE_21:
            case STATE_22:
            case STATE_23:
            case STATE_24:
            case STATE_25:
            case STATE_26:
            case STATE_27:
            case STATE_28:
            case STATE_29:
            case STATE_30:
            case STATE_31:
            case STATE_32:
            case STATE_33:
            case STATE_34:
            case STATE_35:
            case STATE_36:
            case STATE_37:
            case STATE_38:
            case STATE_39:
            case STATE_40:
            case STATE_41:
            case STATE_42: // Comment state. If a newline breaks the comment, return to State 1.
                if (!curr.equals('\n')) break;
                currentState = States.STATE_1;
                break;

            case STATE_43:
            case STATE_44:
            case STATE_45:
            case STATE_46:
            case STATE_47:
            case STATE_48:
            case STATE_49:
            case STATE_50:
            case STATE_51:
            case STATE_52:
            case STATE_53:
            case STATE_54:
            case STATE_55:
            case STATE_56:
            case STATE_57:
            case STATE_58:
            case STATE_59:
            case STATE_60:
            case STATE_61:
            case STATE_62:
            case STATE_63:
            case STATE_64:
            case STATE_65:
            case STATE_66:
            case STATE_67:
            case STATE_68:
            case STATE_69:
            case STATE_70:
            case STATE_71:
            case STATE_72:
            case STATE_73:
            case STATE_74:
            case STATE_75:
            case STATE_76:
            case STATE_77:
            case STATE_78:
            case STATE_79:
        }
        return Tokens.INDETERMINATE;
    }

}
