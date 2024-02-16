package fa.dfa;

import fa.State;

/**
 * Represents a state within a DFA.
 * Extends the State class to include comparison capabilities to support
 * in ordering collections.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class DFAState extends State implements Comparable<DFAState>{

    /**
     *
     * @param name name for state
     */
    public DFAState(String name) {
        super(name);
    }

    /**
     *
     * @param state the object to be compared.
     * @return int for comparison of states, -1 if less than, 0 if equal, and 1 if greater than
     */
    public int compareTo(DFAState state) {
        return state.getName().compareTo(this.getName());
    }
}
