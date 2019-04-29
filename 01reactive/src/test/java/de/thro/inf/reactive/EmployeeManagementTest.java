package de.thro.inf.reactive;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeManagementTest {

    @Test
    public void testNotify() {
        Event e = new Event("DE:12:G7", Direction.LEFT);
        EmployeeManagement eh = EmployeeManagement.getInstance();
        eh.notify(e);

    }
}