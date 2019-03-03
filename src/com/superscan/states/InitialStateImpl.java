package com.superscan.states;

import com.superscan.enums.Tokens;
import com.superscan.transitions.TransitionImpl;

public class InitialStateImpl extends AbstractState {

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public InitialStateImpl() {
        this(false, Tokens.INDETERMINATE);
    }

    /**
     * the initial state needs to transition back to itself on whitespaces
     */
    public InitialStateImpl(final boolean isFinal, final Tokens tokenType) {
        super(isFinal, tokenType);
        addTransition(new TransitionImpl(' ', this));
        addTransition(new TransitionImpl('\t', this));
        addTransition(new TransitionImpl('\n', this));
        setInitialState(this);
    }

}
