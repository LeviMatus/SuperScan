package main.com.superscan.dfa;

import main.com.superscan.Token;

import java.util.List;

public interface DFA {

    DFA transition(final Character c) throws InvalidTokenException;

    List<Token> getAcceptedTokens();

}
