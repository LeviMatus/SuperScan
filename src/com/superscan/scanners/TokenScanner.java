package com.superscan.scanners;

abstract public class TokenScanner implements ScannerInterface {

    // Starting position in line for char and current position in token.
    private Integer start, pos;
    private String rejectedToken;

    StringScanner.States currentState;
    StringScanner.States goalState;
    StringScanner.Tokens tokenType;

    /**
     * To be called before every scan. Ensure we start from scratch
     * and have a clean slate for the new token.
     *
     * @param start Index of where the token starts in the line.
     */
    private void setup(Integer start) {
        this.currentState = StringScanner.States.STATE_1;
        this.start = start;
    }

    Integer getStart() {return this.start;}
    Integer getPos() {return this.pos;}
    public String getRejectedToken() {return this.rejectedToken;}

    /**
     * Reject the token are reason about whether it should be treated
     * as a single or multi character token.
     *
     * @param token The string which will be rejected.
     */
    private void reject(String token) {
        if (errorLocation().equals(this.start)) {
            this.rejectedToken = String.valueOf(token.charAt(0));
            return;
        }
        this.rejectedToken = token;
    }

    /**
     * Iterate over all characters in a token string.
     * If any state transition is invalid, then reject the token. Otherwise, if upon processing the last character,
     * we find ourselves in the end state, we succeed. Return the class' token. Otherwise, return INVALID.
     *
     * @param token A String to be scanned.
     * @param start The starting index for the current line.
     * @return a Tokens enum entry which reflects the language's acceptance of the token.
     */
    public StringScanner.Tokens scan(String token, Integer start) {
        this.setup(start);
        this.pos = 0;

        StringScanner.Tokens result;
        for (Character curr : token.toCharArray()) {
            result = transitionFunction(curr);
            if (result.equals(StringScanner.Tokens.INVALID)) {
                this.reject(token);
                return result;
            }
            this.pos++;
        }
        if (currentState.equals(this.goalState)) return this.tokenType;
        this.reject(token);
        return StringScanner.Tokens.INVALID;
    }

    /**
     * A transition function function which mimics the state changing logic of a DFA.
     * This must be implemented by the developer.
     *
     * @param curr The current character which to scan and evaluate
     * @return Tokens enum type. INVALID if a unsupported State transition is
     * attempted. INDETERMINATE if the decision has yet to be made. This method
     * should not accept a state, simply transition. The calling method, {@link #scan(String, Integer)},
     * will ultimately accept a token or not.
     */
    abstract protected StringScanner.Tokens transitionFunction(Character curr);
}
