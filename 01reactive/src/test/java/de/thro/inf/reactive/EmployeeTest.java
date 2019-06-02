package de.thro.inf.reactive;

import de.thro.inf.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.thro.inf.Direction.LEFT;
import static de.thro.inf.Direction.RIGHT;

/**
 * Test class for the Employee class.
 * Tests the various transitions of states of an employee.
 *
 * @author Thomas Meza on 29.04.2019
 */
public class EmployeeTest {

    private Employee m1;
    private Employee m2;
    private Employee m3;
    private Employee m4;
    private Employee m5;
    private Employee m6;

    /**
     * Sets up the Employee instances.
     */
    @Before
    public void setUp() {
        m1 = new Employee();
        m2 = new Employee();
        m3 = new Employee();
        m4 = new Employee();
        m5 = new Employee();
        m6 = new Employee();
    }

    /**
     * Tests if the employee can move from the state Absent to InTransition.
     */
    @Test
    public void fromAbsentIntoTransition() {
        m1.transition(LEFT);
        Assert.assertEquals("State = IN_TRANSITION.", m1.getCurrentState(), Employee.State.IN_TRANSITION);
    }

    /**
     * Tests if the employee can move from the state Absent to Present.
     */
    @Test
    public void fromAbsentIntoPresent() {
        m2.transition(LEFT);
        m2.transition(RIGHT);
        Assert.assertEquals("State = PRESENT", m2.getCurrentState(), Employee.State.PRESENT);
    }

    /**
     * Tests if the employee stays in the Error state.
     */
    @Test
    public void stayInError() {
        m2.transition(LEFT);
        m2.transition(LEFT);
        m2.transition(RIGHT);
        Assert.assertEquals("State = ERROR", m2.getCurrentState(), Employee.State.ERROR);
    }

    /**
     * Tests if the employee can move from the state Absent to Error.
     */
    @Test
    public void fromAbsentIntoError() {
        m3.transition(RIGHT);
        Assert.assertEquals("State: ERROR", m3.getCurrentState(), Employee.State.ERROR);
    }

    /**
     * Tests if the employee can move from the state Absent to Present and back to Absent.
     */
    @Test
    public void fromAbsentIntoPresentIntoAbsent() {
        m4.transition(LEFT);
        m4.transition(RIGHT);
        m4.transition(RIGHT);
        m4.transition(LEFT);
        Assert.assertEquals("State: ABSENT", m3.getCurrentState(), Employee.State.ABSENT);
    }

    /**
     * Tests if the employee can move from the state Present to Error.
     */
    @Test
    public void fromPresentIntoError() {
        m5.transition(LEFT);
        m5.transition(RIGHT);
        m5.transition(LEFT);
        Assert.assertEquals("State = ERROR", m5.getCurrentState(), Employee.State.ERROR);
    }

    /**
     * Tests if the employee's state is changed into Absent when he enters the Error state.
     */
    @Test
    public void notifyErrorReset() {
        m6.transition(Direction.RIGHT);
        m6.setStateToDefault();
        Assert.assertEquals("State of the employee should be ABSESNT",
                Employee.State.ABSENT, m6.getCurrentState());
    }

}