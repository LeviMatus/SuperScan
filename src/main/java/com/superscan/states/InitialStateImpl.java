package com.superscan.states;

import com.superscan.dfa.DFAImpl;
import com.superscan.dfa.InvalidTokenException;
import com.superscan.enums.CharTypeEnum;
import com.superscan.enums.TokenEnum;
import com.superscan.transitions.TransitionImpl;

public class InitialStateImpl extends AbstractState {

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public InitialStateImpl() {
        this(false, TokenEnum.INDETERMINATE);
    }

    /**
     * the initial state needs to transition back to itself on whitespaces
     */
    public InitialStateImpl(final boolean isFinal, final TokenEnum tokenType) {
        super(isFinal, tokenType);
        addTransition(new TransitionImpl(' ', this));
        addTransition(new TransitionImpl('\t', this));
        addTransition(new TransitionImpl('\n', this));
        setInitialState(this);
    }

    @Override
    public State delimitWithWhitespace(final Character c, final State state, final DFAImpl dfa) {
        dfa.handleWhitespace(c);
        return state;
    }

}
