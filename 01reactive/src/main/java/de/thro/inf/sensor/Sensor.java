package de.thro.inf.sensor;

import com.google.gson.Gson;
import de.thro.inf.Direction;

import de.thro.inf.reactive.Event;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class Sensor.
 * Sensor as client. Generates packets and sends  it to the server.
 *
 * @author Thomas Meza on 24.05.2019
 */
public class Sensor {

    /**
     * Sensor Socket.
     */
    private Socket socket;
    /**
     * Direction (ether left or right).
     */
    private Direction direction;
    /**
     * Static logger for the Sensor-Class.
     */
    private static final Logger sensorLogger = Logger.getLogger(Sensor.class.getName());

    private InputHandler inputHandler;

    /**
     * Creates a new Socket. The socket's parameters inetAddress and Port are entered by the user. If not, they'll be
     * set to a default value.
     * Calls the createLog() method which creates the file handler and sets the logger levels.
     *
     * @param configuration contains entered inetAddress and port number.
     */
    public Sensor(Configuration configuration) {
        try {
            socket = new Socket(configuration.getInetAddress(), configuration.getPort());
            direction = configuration.getDir();
            System.out.println("Socket opened: " +
                    configuration.getInetAddress().toString() + ":" + configuration.getPort());

            createLog();
        } catch (ConnectException c) {
            sensorLogger.severe("Could not connect to server: " + configuration.toString() + ". Connection refused.");
        } catch (IOException e) {
            sensorLogger.severe("An error occurred while trying to open socket.");
            e.printStackTrace();
        }
    }

    /**
     * Creates packet to test the client- server connection via creating random test data.
     *
     * @return a string (JSON) which contains the Event's direction and ID;
     */
    public String createPacket() {
        //takes random sensor. Ether left sensor or right sensor.
        Random random = new Random();
        String id = "";
        for (int i = 0; i < 5; ++i) {
            int number = random.nextInt();
            if (number < 0) {
                number *= -1;
            }
            id += Integer.toHexString(number % 256);
            if (i < 4) {
                id += ":";
            }
        }
        return new Gson().toJson(new Event(direction, id));
    }

    /**
     * Aborts the connection with the socket.
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            sensorLogger.severe("Error while trying to close.");
            e.printStackTrace();
        }
    }

    /**
     * Creates a log file for the sensor.
     * Sets the logger's level to ALL (everything will be documented).
     */
    private void createLog() {
        sensorLogger.setLevel(Level.ALL);
        FileHandler clientHandler = null;
        try {
            clientHandler = new FileHandler("Clients.log");
            clientHandler.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Can't create file handler.");
            e.printStackTrace();
        }
    }

    /**
     * Sets the input handler.
     *
     * @param inputHandler the input handler.
     */
    void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    /**
     * Returns the current Sensor-Logger.
     *
     * @return sensor Logger.
     */
    Logger getLogger() {
        return sensorLogger;
    }

    /**
     * Starts the sensor.
     * Creates an instance of the input handler which allows to abort the connection with the sensor.
     *
     * @param args arguments to be passed.
     */
    public static void main(String[] args) {
        Sensor sensor = new Sensor(new Configuration(args));
        InputHandler inputHandler = new InputHandler(sensor);
        inputHandler.start();
        sensor.setInputHandler(inputHandler);
        sensor.start();
    }

    /**
     * Calls the createPacket() method which creates a random packet and prints the result.
     */
    void start() {
        if (socket == null) {
            return;
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            sensorLogger.severe("An error occurred while trying to write to the output stream.");
            e.printStackTrace();
        }

        while (true) {
            out.println(createPacket());
            out.flush();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
