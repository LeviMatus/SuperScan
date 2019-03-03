package com.superscan.states;

import com.superscan.dfa.DFAImpl;
import com.superscan.enums.Tokens;
import com.superscan.transitions.TransitionImpl;

public class CommentStateImpl extends AbstractState {

    /**
     * Default constructor sets isFinal to false by default.
     * Behaviour is changed by calling StateImpl(true).
     */
    public CommentStateImpl() {
        this(false, Tokens.INDETERMINATE);
    }

    /**
     * the initial state needs to transition back to itself on whitespaces
     */
    public CommentStateImpl(final boolean isFinal, final Tokens tokenType) {
        super(isFinal, tokenType);
        addTransition(new TransitionImpl('\n', getInitialState()));
    }

    @Override
    public State handleDelimitation(final Character c, final State state, final DFAImpl dfa) {
        dfa.handleWhitespace(c);
        return state;
    }

}
