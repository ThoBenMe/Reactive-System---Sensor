package de.thro.inf.reactive;

import de.thro.inf.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the Direction class.
 * Tests if the direction value can be parsed to a string.
 *
 * @author Thomas Meza on 27.05.2019
 */
public class DirectionTest {

    private String direction1;
    private String direction2;

    /**
     * Sets up the direction strings.
     */
    @Before
    public void setUp() {
        direction1 = "left";
        direction2 = "right";
    }

    /**
     * Tests if the direction value LEFT can be parsed to a string.
     */
    @Test
    public void parseLeft() {
        assertSame(Direction.LEFT, Direction.parse(direction1));
    }

    /**
     * Tests if the direction value RIGHT can be parsed to a string.
     */
    @Test
    public void parseRight() {
        assertSame(Direction.RIGHT, Direction.parse(direction2));
    }

}
