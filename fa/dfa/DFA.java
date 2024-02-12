package fa.dfa;

import fa.State;
import fa.dfa.DFAInterface;

import java.util.Set;

public abstract class DFA implements DFAInterface {
    // Could use Set, LinkedHashSet, TreeSet or HashSet to track alphabet/states
    // Transitions could be seperate class or use HashMap to keep track of transitions

    /**
     * Add constructor here
     */

    @Override
    public boolean addState(String name) {
        return false;
    }

    @Override
    public boolean setFinal(String name) {
        return false;
    }

    @Override
    public boolean setStart(String name) {
        return false;
    }

    @Override
    public void addSigma(char symbol) {

    }

    @Override
    public boolean accepts(String s) {
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return null;
    }

    @Override
    public State getState(String name) {
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return false;
    }

    @Override
    public boolean isStart(String name) {
        return false;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        return false;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        return null;
    }
}
