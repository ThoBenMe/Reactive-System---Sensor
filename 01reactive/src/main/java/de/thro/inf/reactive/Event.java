package de.thro.inf.reactive;

public class Event {

    private String ID;
    private Direction direction;

    public Event(String id, Direction direction) {
        this.ID = id;
        this.direction = direction;
    }

    public String getID() {
        return ID;
    }

    public Direction getDirection() {
        return direction;
    }
}