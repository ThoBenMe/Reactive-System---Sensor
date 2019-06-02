package de.thro.inf.sensor;

import de.thro.inf.Direction;
import de.thro.inf.NetworkConfiguration;

/**
 * Contains the same data as the NetworkConfiguration plus the (optional) entered Direction.
 *
 * @author Thomas Meza on 26.05.2019
 */
public class Configuration extends NetworkConfiguration {

    /**
     * Direction (left, right).
     */
    private Direction dir;

    /**
     * Gets the arguments from the NetworkConfiguration.
     * Assigns LEFT to the direction as the default value.
     *
     * @param args the arguments from NetworkConfiguration.
     */
    public Configuration(String[] args) {
        super(args);
        dir = Direction.LEFT;
    }

    /**
     * Returns the direction. Even if the user entered more than three parameters, the direction will be set to
     * the second parameter.
     *
     * @return direction (left, right).
     */
    public Direction getDir() {
        if (super.args.length >= 3) {
            dir = Direction.parse(args[2]);
        }
        return dir;
    }

    /**
     * Returns a string which contains the NetworkConfiguration String as well as the direction.
     *
     * @return information string.
     */
    @Override
    public String toString() {
        return super.toString() + " (" + dir.toString() + ")";
    }

}
