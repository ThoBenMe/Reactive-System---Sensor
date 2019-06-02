package de.thro.inf.reactive;

import de.thro.inf.Direction;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

/**
 * Class EmployeeManagement.
 * Manages all the employees. Creates logger files and manages the list of employees.
 *
 * @author Thomas Meza on 07.05.2019
 */
public class EmployeeManagement {

    /**
     * Singleton instance of the Employee Management class.
     */
    private static volatile EmployeeManagement instance = null;
    /**
     * A list which contains all the registered employees.
     */
    private static final List<Employee> employees = new LinkedList<>();
    /**
     * Static logger for the Management-class which logs all the events that happen.
     */
    private static final Logger eventLogger = Logger.getLogger(EmployeeManagement.class.getName());
    /**
     * Static logger for the Management-class which logs the general operating system information.
     */
    private static final Logger infoLogger = Logger.getLogger("de.thro");


    /**
     * Calls the logger's instantiating methods.
     */
    private EmployeeManagement() {
        setUpLogger();
        logEnvironment();
    }

    /**
     * Singleton getter. Returns the class' instance.
     *
     * @return instance of type Employee.
     */
    synchronized static EmployeeManagement getInstance() {
        if (instance == null) {
            instance = new EmployeeManagement();
            eventLogger.info("New instance created.");
        }
        return instance;
    }

    /**
     * Method to set up the loggers and the file handlers.
     * Default settings are set to print all logger levels.
     */
    private void setUpLogger() {
        LogManager.getLogManager().reset(); //to get rid of everything that has been written on the root
        eventLogger.setLevel(Level.ALL);
        infoLogger.setLevel(Level.ALL);

        try {
            FileHandler infoHandler = new FileHandler("GeneralInformation.log");
            FileHandler eventHandler = new FileHandler("Events.log");

            infoHandler.setLevel(Level.ALL);
            eventHandler.setLevel(Level.ALL);
            //Because of the simplicity of searching through XML files, the usage of the formatter is not used here.
            //It is more inconvenient to look through a formatted log file.
            /*
            SimpleFormatter formatter = new SimpleFormatter();
            eventHandler.setFormatter(formatter);
            infoHandler.setFormatter(formatter);
            */
            eventLogger.addHandler(eventHandler);
            infoLogger.addHandler(infoHandler);
        } catch (Exception e) {
            eventLogger.severe("File eventLogger not working.");
            e.printStackTrace();
        }
    }

    /**
     * Prints and logs the common Operating system information which are stored in the GeneralInformation.log.
     */
    private static void logEnvironment() {
        infoLogger.info("Java Properties");
        infoLogger.info("Operating System: " + System.getProperty("os.name"));
        infoLogger.info("OS-Version: " + System.getProperty("os.version"));
        infoLogger.info("User: " + System.getProperty("user.name"));
        infoLogger.info("Java Home: " + System.getProperty("java.home"));
        infoLogger.info("Java Version: " + System.getProperty("java.version"));
        infoLogger.info("Java Vendor: " + System.getProperty("java.vendor"));
        infoLogger.info("Class Path: " + System.getProperty("java.class.path"));
    }

    /**
     * Searches for an employee by the ID in the employees list. If found, shift() is called with Event e's
     * current direction.
     * If the ID couldn't be found, it creates a new employee and adds it to the employees list.
     * If the employee is caught in a state of error, the employee's current state is reset back to absent.
     *
     * @param e contains the ID and Direction with which the employee should be found.
     */
    boolean notify(Event e) {
        eventLogger.info("Searching for ID: " + e.getID() + "...");
        eventLogger.info("Current direction: " + e.getDirection());

        String id = e.getID();
        Direction direction = e.getDirection();

        if (id == null || direction == null) {
            return false;
        }

        Employee employee;

        for (Employee tmp : employees) {
            if (tmp.getID() != null && tmp.getID().equals(id)) {
                employee = tmp;
                eventLogger.info("Employee found! ID: " + employee.getID() + ". Changing direction...");
                employee.transition(e.getDirection());
                if (employee.getCurrentState().equals(Employee.State.ERROR)) {
                    eventLogger.info("Caught in error state. Resetting current state to default (ABSENT).");
                    employee.setStateToDefault();
                }
                return true;
            }
        }

        //if not existing, create a new one
        eventLogger.info("Couldn't find ID. Creating a new one...");
        employee = new Employee();
        employee.setID(id);
        employees.add(employee);
        employee.transition(e.getDirection());
        eventLogger.info("New Employee added. Employee ID: " + employee.getID() + ", Current state: "
                + employee.getCurrentState());
        return false;
    }

}

