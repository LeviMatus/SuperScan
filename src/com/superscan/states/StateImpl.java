package com.superscan.states;

import com.superscan.enums.Tokens;
import com.superscan.transitions.TransitionImpl;

public class StateImpl extends AbstractState {

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public StateImpl() {
        this(false, Tokens.INDETERMINATE);
    }

    public StateImpl(final boolean isFinal, final Tokens tokenType) {
        super(isFinal, tokenType);

        this.addTransition(new TransitionImpl(' ', getInitialState()));
        this.addTransition(new TransitionImpl('\t', getInitialState()));
        this.addTransition(new TransitionImpl('\n', getInitialState()));
    }

}
