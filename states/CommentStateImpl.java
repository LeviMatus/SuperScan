package superscan.states;

import superscan.dfa.DFAImpl;
import superscan.enums.TokenEnum;
import superscan.transitions.TransitionImpl;
import superscan.states.AbstractState;
import superscan.states.State;


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
