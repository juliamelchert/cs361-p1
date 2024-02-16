package fa.dfa;

import fa.State;
import fa.dfa.DFAInterface;

import java.util.*;

import java.util.Set;

/**
 *
 */
public class DFA implements DFAInterface {
    // Could use Set, LinkedHashSet, TreeSet or HashSet to track alphabet/states
    // Transitions could be seperate class or use HashMap to keep track of transitions
    private TreeSet<DFAState> treeState;
    private TreeSet<DFAState> finalState;
    private TreeSet<Character> alphabet;
    private DFAState stringState;
    private ArrayList<String> qStates;
    private ArrayList<String> finalS;
    private ArrayList<Character> sigma;
    private Map<DFAState, ArrayList<Map<Character, DFAState>>> transition;


    public DFA() {
        this.treeState = new TreeSet<DFAState>();
        this.finalState = new TreeSet<DFAState>();
        this.alphabet = new TreeSet<Character>();
        this.stringState = null;
        this.qStates = new ArrayList<String>();
        this.finalS = new ArrayList<String>();
        this.sigma = new ArrayList<Character>();
        this.transition = new HashMap<DFAState,  ArrayList<Map<Character, DFAState>>>();

    }

    public DFA(TreeSet<DFAState> treeState, TreeSet<DFAState> finalState, TreeSet<Character> alphabet,
               Map<DFAState, ArrayList<Map<Character, DFAState>>> transition, DFAState stringState) {
        this.treeState = treeState;
        this.finalState = finalState;
        this.alphabet = alphabet;
        this.transition = transition;
        this.stringState = stringState;
    }

    @Override
    public boolean addState(String name) {

        //Iterate through to find the point to add the next State
        Iterator<DFAState> check = treeState.iterator();
        while(check.hasNext()) {
            //Check if the state already exists
            if(check.next().getName().equals(name)) {
                return false;
            }
        }
        this.qStates.add(name);
        //DFAState must be abstract fix this later

        this.treeState.add(new DFAState(name));
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        Iterator<DFAState> check = treeState.iterator();
        boolean bool = false;
        while(check.hasNext()) {
            if(check.next().getName().equals(name)) {
                finalS.add(name);
                finalState.add(new DFAState(name));
                bool = true;
            }
        }
        return bool;
    }

    @Override
    public boolean setStart(String name) {
        Iterator<DFAState> check = treeState.iterator();
        boolean bool = false;
        while(check.hasNext()) {
            DFAState curr = check.next();
            if(check.next().getName().equals(name)) {
                this.stringState = curr;
                bool = true;
            }
        }
        return bool;
    }

    @Override
    public void addSigma(char symbol) {
        alphabet.add(symbol);
        this.sigma.add(symbol);
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
