package com.superscan.states;

import com.superscan.dfa.DFAImpl;
import com.superscan.enums.TokenEnum;
import com.superscan.transitions.TransitionImpl;

public class CommentStateImpl extends AbstractState {

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public CommentStateImpl() {
        this(false, TokenEnum.INDETERMINATE);
    }

    /**
     * the initial state needs to transition back to itself on whitespaces
     */
    public CommentStateImpl(final boolean isFinal, final TokenEnum tokenType) {
        super(isFinal, tokenType);
        addTransition(new TransitionImpl('\n', getInitialState()));
    }

    @Override
    public State delimit(DFAImpl dfa, Character c, State state) {
        dfa.handleWhitespace(c);
        return state;
    }

}
