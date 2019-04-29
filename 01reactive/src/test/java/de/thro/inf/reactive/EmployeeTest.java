package de.thro.inf.reactive;

import org.junit.Assert;
import org.junit.Test;

import static de.thro.inf.reactive.Direction.LEFT;
import static de.thro.inf.reactive.Direction.RIGHT;


public class EmployeeTest {

    @Test
    public void testShift() {
        Employee m = new Employee();
        Employee m2 = new Employee();
        Employee m3 = new Employee();
        //test, if an employee can reach the state PRESENT and from there on to ERROR
        m.transition(LEFT);
        Assert.assertEquals("State = IN_TRANSITION.", m.getCurrentState(), Employee.State.IN_TRANSITION);
        m.transition(RIGHT);
        Assert.assertEquals("State = PRESENT", m.getCurrentState(), Employee.State.PRESENT);
        m.transition(LEFT);
        Assert.assertEquals("State = ERROR", m.getCurrentState(), Employee.State.ERROR);
        m.transition(LEFT);
        Assert.assertEquals("State: ERROR", m.getCurrentState(), Employee.State.ERROR);
        //test, if an employee reaches ERROR if he turns RIGHT at the start
        m2.transition(RIGHT);
        Assert.assertEquals("State: ERROR", m2.getCurrentState(), Employee.State.ERROR);
        //test, if an employee reaches ABSENT from IN_TRANSITION
        m3.transition(LEFT);
        m3.transition(LEFT);
        Assert.assertEquals("State: ABSENT", m3.getCurrentState(), Employee.State.ABSENT);
    }

}