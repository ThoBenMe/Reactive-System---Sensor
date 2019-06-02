package de.thro.inf.reactive;

import de.thro.inf.sensor.Configuration;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * The server creates an instance of the client acceptor class with the configuration's parameters.
 * Takes events from the queue and sends them to the Management-class.
 * Enqueues elements to the blocking queue.
 *
 * @author Thomas Meza on 26.05.2019
 */
public class Server extends Thread {

    /**
     * Client acceptor variable.
     */
    private ClientAcceptor clientAcceptor;
    /**
     * Static logger for the server.
     */
    private static final Logger networkLogger = Logger.getLogger(Server.class.getName());
    /**
     * Static executor.
     */
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    /**
     * Blocking Queue for Events.
     */
    private static final BlockingQueue<Event> eventBlockingQueue = new LinkedBlockingQueue<>();

    /**
     * Starts the client acceptor (in a new thread) which accepts incoming clients.
     *
     * @param configuration contains the inetAddress and the port number.
     */
    private Server(Configuration configuration) {
        clientAcceptor = new ClientAcceptor(configuration);
        executor.execute(clientAcceptor);
    }

    /**
     * Takes an object from the blocking queue and sends it to the employee management instance.
     * Calls Employee-Management's notify method.
     */
    public void run() {
        EmployeeManagement employeeManagement = EmployeeManagement.getInstance();
        while (true) {
            try {
                employeeManagement.notify(eventBlockingQueue.take());
            } catch (InterruptedException e) {
                networkLogger.severe("An error occurred while trying to transfer the event to the management.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Allows to safely close the server.
     */
    void close() {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            networkLogger.severe("An error occurred while trying to shut down the server.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the executor instance.
     *
     * @returns the executor instance.
     */
    public static ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Method to put an Event into the blocking queue.
     *
     * @param e that shall be put into the blocking queue.
     */
    public static void enqueueEvent(Event e) {
        try {
            eventBlockingQueue.put(e);
            networkLogger.info("Enqueueing event successful.");
        } catch (InterruptedException ie) {
            networkLogger.severe("An error occurred while trying to put the event into the blocking queue.");
            ie.printStackTrace();
        }
    }

    /**
     * Main method which start the server.
     * Checks if the user enters 'exit'. If that's the case, the close()-method will be called to close the server.
     *
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server(new Configuration(args));
        server.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.next().toLowerCase();
            if (command.equals("exit") || command.equals("stop") || command.equals("quit")) {
                server.close();
                System.exit(0);
            }
        }
    }
}

