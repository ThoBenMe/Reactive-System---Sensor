package de.thro.inf.reactive;

import de.thro.inf.Direction;

/**
 * Contains the mealy, getter and transition method, which is required to change the employee's state.
 *
 * @author Thomas Meza on 29.04.2019
 */
public class Employee {

    /**
     * Enum State.
     * Represents the four possible states in which a employee can be.
     */
    public enum State {
        ABSENT, IN_TRANSITION, PRESENT, ERROR
    }

    /**
     * The employee's ID.
     */
    private String ID;
    /**
     * The employee's default state.
     */
    private State currentState = State.ABSENT;

    /**
     * Two-dimensional array to represent the mealy.
     */
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
     * Returns the current state of the mealy machine.
     *
     * @return currentState of the mealy
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Returns the employee's ID.
     *
     * @return current ID of the employee.
     */
    public String getID(){
        return ID;
    }

    /**
     * Sets an ID for the employee.
     *
     * @param ID to be setted.
     */
    public void setID(String ID){
        this.ID = ID;
    }

    /**
     * Default state setter.
     * If the Employee is caught in an error state, the employee's state is reset back to absent.
     */
    public void setStateToDefault(){
        this.currentState = State.ABSENT;
    }

}
