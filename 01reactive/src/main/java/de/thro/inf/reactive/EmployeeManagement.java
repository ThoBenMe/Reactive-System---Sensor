package de.thro.inf.reactive;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class EmployeeManagement {

    private static volatile EmployeeManagement instance = null;
    // create list to manage employees
    private static final List<Employee> employees = new LinkedList<>();
    private static final Logger logger = Logger.getLogger("de.thro");


    /**
     * Singleton constructor
     */
    private EmployeeManagement() {
        //if (instance != null) {
          //  throw new RuntimeException("Use getInstance() method to create!");
        }


    /**
     * Singleton getter with lazy loading and synchronized implemented.
     *
     * @return instance of type Employee
     */
    public static EmployeeManagement getInstance() {
        if (instance == null) {
            synchronized (EmployeeManagement.class) {
                if (instance == null) {
                    instance = new EmployeeManagement();
                    logger.info("New instance created.");
                }
            }
        }
        return instance;
    }


    /**
     * Searches for an employee by the ID in the employees list. If found, shift() is called with Event e's
     * current direction.
     * If the ID couldn't be found, it creates a new employee and adds it to the employees list.
     *
     * @param e contains the ID and Direction with which the employee should be found.
     */
    public void notify(Event e) {
        logger.info("Searching for ID: " + e.getID() + "...");
        logger.info("Current direction: " + e.getDirection());

        Employee employee = null;

        for (Employee tmp : employees) {
            if (tmp.getID().equalsIgnoreCase(e.getID())) {
                employee = tmp;
                logger.info("Employee found! ID: " + employee.getID() + ". Changing direction...");
                employee.transition(e.getDirection());
                break;
            }
        }
        //if not existing, create a new one
        if (employee == null) {
            logger.info("Couldn't find ID. Creating a new one...");
            employee = new Employee();
            employees.add(employee);
            employee.transition(e.getDirection());
            logger.info("New Employee added. Employee ID: " + employee.getID() + ", Current state: "
                    + employee.getCurrentState());
            return;
        }

        logger.severe("Unknown error occurred. Could not find or create employee.");
    }

}
