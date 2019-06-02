package de.thro.inf.sensor;

import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Allows to enter a command (exit, stop, ...) to abort the connection with the sensor.
 *
 * @author Thomas Meza on 27.05.2019
 */
public class InputHandler implements Runnable {

    /**
     * Executor with one thread.
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    /**
     * Sensor variable.
     */
    private Sensor sensor;

    /**
     * Assigns the sensor parameter to the class' sensor variable.
     *
     * @param sensor sensor variable.
     */
    InputHandler(Sensor sensor) {
        this.sensor = sensor;
    }

    /**
     * Starts sensor thread.
     */
    void start() {
        executorService.execute(this);
    }

    /**
     * Run method.
     * Awaits the user's abortion input (stop, exit, ...) and aborts the sensor connection.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String command = "";

        while (true) {
            command = scanner.nextLine().toLowerCase();

            if (command.equals("exit") || command.equals("stop") || command.equals("quit")) {
                sensor.getLogger().info("Socket disconnected.");
                executorService.shutdownNow();
                try {
                    executorService.awaitTermination(1, TimeUnit.SECONDS);
                    sensor.close();
                } catch (InterruptedException e) {
                }
                System.out.println("Closing the sensor...");

                System.exit(0);
            }
        }
    }
}
