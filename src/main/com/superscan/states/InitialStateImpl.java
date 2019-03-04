package main.com.superscan.states;

import main.com.superscan.dfa.DFAImpl;
import main.com.superscan.enums.Tokens;
import main.com.superscan.transitions.TransitionImpl;

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

    @Override
    public State handleDelimitation(final Character c, final State state, final DFAImpl dfa) {
        dfa.handleWhitespace(c);
        return state;
    }

}
