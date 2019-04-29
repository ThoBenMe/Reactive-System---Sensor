package de.thro.inf.reactive;

public class Employee {

    //enums: state declares the possible states the system can be, input shows the alphabet of input
    public enum State {
        ABSENT, IN_TRANSITION, PRESENT, ERROR
    }

    //the employee's ID
    private String ID;
    //first state
    private State currentState = State.ABSENT;


    private static State transitionTable[][] = {{State.IN_TRANSITION, State.ABSENT, State.ERROR, State.ERROR},
            {State.ERROR, State.PRESENT, State.IN_TRANSITION, State.ERROR}};

    /**
     * Implements the transition between one state and another.
     *
     * @param currentState the current state as parameter
     * @param input        character of the input alphabet
     * @return next state
     */
    State trigger(State currentState, Direction input) {
        return transitionTable[input.ordinal()][currentState.ordinal()];
    }

    /**
     * Implementation of the state transition.
     *
     * @param input from the input alphabet Input.
     * @return the updated current state.
     */
    public State transition(Direction input) {
        currentState = trigger(currentState, input);
        return currentState;
    }

    /**
     * Getter for 'currentState' - variable
     *
     * @return currentState of the mealy
     */
    public State getCurrentState() {
        return currentState;
    }

    public String getID(){
        return ID;
    }

}
