package fa.dfa;

import fa.State;
import fa.dfa.DFAInterface;

import java.util.*;

import java.util.Set;

/**
 * Represents a deterministic finite automaton (DFA) comprised of DFAState objects and transitions.
 * Manages the appropriate data for a DFA like the alphabet, states, start/final states, and transition table.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class DFA implements DFAInterface {
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSigma(char symbol) {
        alphabet.add(symbol);
        this.sigma.add(symbol);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accepts(String s) {

        // check for empty DFA/start state
        if (this.stringState == null) {
            // account for empty string
            if (s == "") {
                return true;
            } else {
                return false;
            }
        }

        DFAState currState = this.getDFAState(this.stringState.getName());
        String currString = s;

        while (currString.length() > 0) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Character> getSigma() {
        return this.alphabet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getState(String name) {
        for(DFAState state : treeState) {
            if(state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    /**
     * Finds and returns the DFAState object with the given name; like getState(), but returns a DFAState
     * @params      the name for state
     * @return      the coresponding DFAState object with the name name
     */
    public DFAState getDFAState(String name) {
        for(DFAState state : treeState) {
            if(state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinal(String name) {
        boolean bool = false;
        for(State state : finalState) {
            if(state.getName().equals(name)) {
                bool = true;
            }
        }
        return bool;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStart(String name) {
        return Objects.equals(stringState.getName(), name);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * Copies state passed through to 'stringState' of a DFAState object
     * @param state DFAState object to be copied
     */
    private void copyInitial(DFAState state) {
        stringState = state;
    }

    /**
     * Prints the transitions of a DFAState object
     * @return      nothing, just prints to console
     */
    public void printTransitions() {
        System.out.println("TRANSITIONS:");
        for(DFAState key : this.transition.keySet()) {
            for(Map<Character, DFAState> item : this.transition.get(key)) {
                System.out.println(key + " -> " + item.values() + " on a " + item.keySet());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        StringBuilder var = new StringBuilder("Q={ ");
        String sigmaTemp = "";
        for (String state : qStates) {
            var.append(state).append(" ");
        }

        var.append("}\nSigma = { ");
        for (Character item : sigma) {
            var.append(item).append(" ");
        }

        var.append(sigmaTemp).append("}\ndelta =\n \t");
        for (Character value : sigma) {
            var.append(value).append("\t");
        }
        var.append("\n");

        for (int i = 0; i < qStates.size(); i++) {
            var.append(qStates.get(i)).append("\t");
            DFAState state = (DFAState) this.getState(qStates.get(i));
            for (int j = 0; j < sigma.size(); j++) {
                ArrayList<Map<Character, DFAState>> transitionTemp = transition.get(state);
                char symbol = sigma.get(j);

                boolean transitionBool = false;

                for (int k = 0; k < transition.size(); k++) {
                    Map<Character, DFAState> symbolTransition = transitionTemp.get(k);

                    if (symbolTransition.containsKey(symbol)) {
                        DFAState nextState = symbolTransition.get(symbol);
                        var.append(nextState.getName()).append("\t");
                        transitionBool = true;
                        break;
                    }
                }

                if (!transitionBool) {
                    var.append("e ");
                }

            }
            var.append("\n");

        }

        var.append("q0 = ").append(stringState.getName()).append("\n").append("F = { ");
        for(String aFinal : finalS) {
            var.append(aFinal).append(" ");
        }
        var.append("}");
        return var.toString();
    }

}
