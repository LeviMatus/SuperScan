package main.com.superscan.states;

import main.com.superscan.dfa.DFAImpl;
import main.com.superscan.enums.Tokens;
import main.com.superscan.transitions.TransitionImpl;

public class StateImpl extends AbstractState {

    private State fallback;
    private String label;

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public StateImpl() {
        this(false, Tokens.INDETERMINATE);
    }

    public StateImpl(boolean stdDelimiter, String label) {
        this(false, Tokens.INDETERMINATE, stdDelimiter, label);
    }

    public StateImpl(boolean stdDelimiter, State fallback, String label) {
        this(false, Tokens.INDETERMINATE, stdDelimiter, fallback, label);
    }

    /**
     * Non-initial states transition back to the initial state on a whitespace character.
     */
    public StateImpl(final boolean isFinal, final Tokens tokenType) {
        super(isFinal, tokenType);
        this.addTransition(new TransitionImpl(' ', getInitialState()));
        this.addTransition(new TransitionImpl('\t', getInitialState()));
        this.addTransition(new TransitionImpl('\n', getInitialState()));
    }

    public StateImpl(final boolean isFinal, final Tokens tokenType, String label) {
        super(isFinal, tokenType);
        this.label = label;
        this.addTransition(new TransitionImpl(' ', getInitialState()));
        this.addTransition(new TransitionImpl('\t', getInitialState()));
        this.addTransition(new TransitionImpl('\n', getInitialState()));
    }

    /**
     * Non-initial states transition back to the initial state on a whitespace character.
     */
    public StateImpl(final boolean isFinal, final Tokens tokenType, final boolean stdDelimiter, String label) {
        super(isFinal, tokenType);
        this.label = label;
        if (stdDelimiter) {
            this.addTransition(new TransitionImpl(' ', getInitialState()));
            this.addTransition(new TransitionImpl('\t', getInitialState()));
            this.addTransition(new TransitionImpl('\n', getInitialState()));
        }
    }

    public StateImpl(final boolean isFinal, final Tokens tokenType, final boolean stdDelimiter, State fallback, String label) {
        this(isFinal, tokenType, stdDelimiter, label);
        this.fallback = fallback;
    }

    @Override
    public State attemptFallback(final DFAImpl dfa) {
        if (this.fallback == null)
            throw new IllegalArgumentException("FAILURE");
        dfa.abort();
        return this.fallback;
    }

}
