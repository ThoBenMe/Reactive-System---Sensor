package de.thro.inf.reactive;

import org.junit.Before;
import de.thro.inf.Direction;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for the EmployeeManagement class.
 * Tests the notify method.
 *
 * @author Thomas Meza on 07.05.2019
 */
public class EmployeeManagementTest {

    private Event eventLeft;

    /**
     * Sets up the Event.
     */
    @Before
    public void setUp() {
        eventLeft = new Event(Direction.LEFT, "FF:FF:FF:FF:FF");
    }

    /**
     * Tests the first access of an employee.
     * The list should be empty.
     */
    @Test
    public void notifyEmptyEmployeeList() {
        assertFalse(EmployeeManagement.getInstance().notify(eventLeft));
    }

    /**
     * Tests the second access of the same employee.
     * The list should contain the mentioned employee.
     */
    @Test
    public void notifyExistingEmployee() {
        assertTrue(EmployeeManagement.getInstance().notify(eventLeft));
    }

    /**
     * Tests if the instance of the management class is correctly built.
     */
    @Test
    public void getInstance() {
        assertNotNull(EmployeeManagement.getInstance());
    }

}