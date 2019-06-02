package de.thro.inf.reactive;

import de.thro.inf.Direction;

/**
 * Class Event.
 * Contains the employee's ID and Direction.
 *
 * @author Thomas Meza on 29.04.2019
 */
public class Event {
    /**
     * The employee's ID.
     */
    private String ID;
    /**
     * The Direction (left, right).
     */
    private Direction direction;

    /**
     * Constructor which assigns the ID and direction.
     *
     * @param direction the direction (left, right).
     * @param id the employee's id.
     */
    public Event(Direction direction, String id) {
        this.direction = direction;
        this.ID = id;
    }

    /**
     * Returns the employee's ID.
     *
     * @return employee's ID.
     */
    public String getID() {
        return ID;
    }

    /**
     * Returns the direction.
     *
     * @return direction (left, right).
     */
    public Direction getDirection() {
        return direction;
    }

}