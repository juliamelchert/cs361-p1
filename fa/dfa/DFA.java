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
            if(curr.getName().equals(name)) {
                this.stringState = curr;
                bool = true;
                break;
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

        DFAState currState = this.stringState;
        String currString = s;

        while (s.length() > 0) {
            Character currChar = currString.charAt(0);
            ArrayList<Map<Character, DFAState>> currCharTransitions = this.transition.get(currState);
            Boolean transitionExists = false;   // tracks if the machine gets stuck

            for (int i = 0; i < currCharTransitions.size(); i++) {
                Map<Character, DFAState> currMap = currCharTransitions.get(i);

                // if there's a transition on the current character, advance
                if (currMap.containsKey(currChar)) {
                    transitionExists = true;
                    currState = currMap.get(currChar);
                    currString = currString.substring(1); // consumes first character
                }
            }

            // if all of the transitions were checked for a state and the currChar is not one of those transitions, return false
            if (transitionExists == false) {
                return false;
            }
        }

        if (this.isFinal(currState.getName())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<Character> getSigma() {
        return this.alphabet;
    }

    @Override
    public State getState(String name) {
        for(DFAState state : treeState) {
            if(state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        boolean bool = false;
        for(State state : treeState) {
            if(state.getName().equals(name)) {
                bool = true;
            }
        }
        return bool;
    }

    @Override
    public boolean isStart(String name) {
        return Objects.equals(stringState.getName(), name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char symb) {
        boolean bool = false;
        DFAState initial = null;
        DFAState end = null;
        Iterator<DFAState> temp = treeState.iterator();
        while(temp.hasNext()) {
            DFAState curr = temp.next();
            if(curr.getName().equals(fromState)) {
                initial = curr;
            }
            if(curr.getName().equals(toState)) {
                end = curr;
            }
        }

        if(initial != null && end != null && alphabet.contains(symb)) {
            bool = true;
            Map<Character, DFAState> destination = new HashMap<Character, DFAState>();
            destination.put(symb, end);
            if(transition.containsKey(initial)) {
                transition.get(initial).add(destination);

            } else {
                ArrayList<Map<Character, DFAState>> newTransition = new ArrayList<Map<Character, DFAState>>();
                newTransition.add(destination);
                transition.put(initial, newTransition);
            }
        }

        return bool;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA swap = new DFA();
        swap.copyInitial(stringState);
        swap.setStart(stringState.getName());

        for(DFAState state : treeState) {
            swap.addState(state.getName());
        }

        for(DFAState state : finalState) {
            swap.setFinal(state.getName());
        }

        for(Character temp : alphabet) {
            swap.addSigma(temp.charValue());
        }

        for(DFAState key : transition.keySet()) {
            for(Map<Character, DFAState> item : transition.get(key)) {
                for(Character temp : item.keySet()) {
                    if(temp == symb1) {
                        swap.addTransition(key.getName(), item.get(temp).getName(), symb2);
                    } else if (temp == symb2) {
                        swap.addTransition(key.getName(), item.get(temp).getName(), symb1);
                    } else {
                        swap.addTransition(key.getName(), item.get(temp).getName(), temp);
                    }
                }
            }
        }
        return swap;
    }

    private void copyInitial(DFAState state) {
        stringState = state;
    }

    public void printTransitions() {
        System.out.println("TRANSITIONS:");
        for(DFAState key : transition.keySet()) {
            for(Map<Character, DFAState> item : transition.get(key)) {
                System.out.println(key + " -> " + item.values() + " on a " + item.keySet());
            }
        }
    }
}
