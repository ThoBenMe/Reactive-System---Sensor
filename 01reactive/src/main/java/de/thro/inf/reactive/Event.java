package de.thro.inf.reactive;

public class Event {

    private String id;
    private Direction direction;

    public Event(String id, Direction direction) {
        this.id = id;
        this.direction = direction;
    }

    public String getID() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }
}
