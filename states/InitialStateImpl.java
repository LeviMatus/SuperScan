package superscan.states;

import superscan.dfa.DFAImpl;
import superscan.dfa.InvalidTokenException;
import superscan.enums.CharTypeEnum;
import superscan.enums.TokenEnum;
import superscan.transitions.TransitionImpl;
import superscan.states.AbstractState;


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

//    @Override
//    public State delimit(DFAImpl dfa, Character c, State state) {
//        dfa.handleWhitespace(c);
//        return state;
//    }

}
