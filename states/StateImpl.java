package superscan.states;

import superscan.dfa.DFAImpl;
import superscan.enums.CharTypeEnum;
import superscan.enums.TokenEnum;
import superscan.transitions.TransitionImpl;


public class StateImpl extends AbstractState {

    private State fallback;

    /**
     * Standard State Constructors
     *
     * Parameters:
     *   isFinal: is this a final state? boolean.
     *   stdDelimiter: does this state use standard whitespace delimiters? Strings do not.
     *   fallbak: If reading a multichar token and an error occurs, where do we fall back to continue tokenizing?
     *   tokenType: If a final state, what type of token is this?
     */

    public StateImpl() {
        this(false, TokenEnum.INDETERMINATE);
    }

    public StateImpl(boolean stdDelimiter) {
        this(false, TokenEnum.INDETERMINATE, stdDelimiter);
    }

    public StateImpl(boolean stdDelimiter, State fallback) {
        this(false, TokenEnum.INDETERMINATE, stdDelimiter, fallback);
    }

    /**
     * Non-initial states transition back to the initial state on a whitespace character.
     */
    public StateImpl(final boolean isFinal, final TokenEnum tokenType) {
        super(isFinal, tokenType);
        this.addTransition(new TransitionImpl(' ', getInitialState()));
        this.addTransition(new TransitionImpl('\t', getInitialState()));
        this.addTransition(new TransitionImpl('\n', getInitialState()));
        if (isFinal && !tokenType.getTokenCharType().equals(CharTypeEnum.SINGLE)) addSingleCharTokenTransitions();

    }

    /**
     * Non-initial states transition back to the initial state on a whitespace character.
     */
    private StateImpl(final boolean isFinal, final TokenEnum tokenType, final boolean stdDelimiter) {
        super(isFinal, tokenType);
        if (stdDelimiter) {
            this.addTransition(new TransitionImpl(' ', getInitialState()));
            this.addTransition(new TransitionImpl('\t', getInitialState()));
            this.addTransition(new TransitionImpl('\n', getInitialState()));
        }
        if (isFinal && !tokenType.getTokenCharType().equals(CharTypeEnum.SINGLE)) addSingleCharTokenTransitions();
    }

    private StateImpl(final boolean isFinal, final TokenEnum tokenType, final boolean stdDelimiter, State fallback) {
        this(isFinal, tokenType, stdDelimiter);
        this.fallback = fallback;
    }

    // Convenience method for adding single char transitions to all states other than string states.
    private void addSingleCharTokenTransitions() {
        for (TokenEnum tokenEnum : getSingleCharStates().keySet()) {
            TransitionImpl trans = null;
            switch (tokenEnum) {
                case CLOSERD: trans = new TransitionImpl(')', getSingleCharStates().get(tokenEnum)); break;
                case OPENRD: trans = new TransitionImpl('(', getSingleCharStates().get(tokenEnum)); break;
                case OPENSQ: trans = new TransitionImpl('[', getSingleCharStates().get(tokenEnum)); break;
                case OPENCU: trans = new TransitionImpl('{', getSingleCharStates().get(tokenEnum)); break;
                case CLOSESQ: trans = new TransitionImpl(']', getSingleCharStates().get(tokenEnum)); break;
                case CLOSECU: trans = new TransitionImpl('}', getSingleCharStates().get(tokenEnum)); break;
                case QUOTEMK: trans = new TransitionImpl('\'', getSingleCharStates().get(tokenEnum)); break;
            }
            this.addTransition(trans);
        }
    }

    /**
     * How to handle invalid characters. Single-char should immediately fail (fallback is null) Strings and identifiers
     * ought to flag the dfa to abort, but continue until delimited.
     *
     * @param c
     * @param dfa
     * @return
     */
    @Override
    public State attemptFallback(final Character c, final DFAImpl dfa) {
        if (this.fallback == null)
            throw new IllegalArgumentException("FAILURE");
        dfa.abort();
        return this.fallback;
    }

}
