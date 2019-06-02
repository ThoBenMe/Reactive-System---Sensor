package de.thro.inf;

/**
 * Contains the directions in which an employee can move as well as the positions of the sensors.
 *
 * @author Thomas Meza on 29.04.2019
 */
public enum Direction {

    LEFT("left"), RIGHT("right");

    private String strName;

    /**
     * Constructor which assigns the string name.
     *
     * @param strName the string name.
     */
    Direction(String strName) {
        this.strName = strName;
    }

    /**
     * Method to parse the macros LEFT and RIGHT to lower case.
     * Assures a more convenient way of transmitting the direction (e.g. typing "left" instead of Direction.LEFT).
     *
     * @param name the direction (LEFT, RIGHT)
     * @return null if name doesn't equal LEFT or RIGHT
     */
    public static Direction parse(String name){
        String copy = name.toLowerCase();
        if (copy.equals(LEFT.toString())) {
            return LEFT;
        }
        if (copy.equals(RIGHT.toString())) {
            return RIGHT;
        }
        return null;
    }

    /**
     * Returns string from the direction.
     */
    @Override
    public String toString(){
        if (this == Direction.LEFT) {
            return "left";
        }
        if (this == Direction.RIGHT) {
            return "right";
        }
        return null;
    }
}
