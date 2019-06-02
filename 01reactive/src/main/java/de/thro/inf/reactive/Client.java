package de.thro.inf.reactive;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * Class Client.
 * Receives and manages data from the server.
 *
 * @author Thomas Meza on 25.05.2019
 */
public class Client extends Thread {

    /**
     * Socket.
     */
    private Socket socket;
    /**
     * Static Logger for the Client-Class.
     */
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

    /**
     * Constructor. Assigns the socket and adds the client to the clientList.
     *
     * @param socket current socket.
     */
    public Client(Socket socket) {
        this.socket = socket;
    }

    /**
     * Run Method.
     * Gets Input Stream from the socket and stores it in a string.
     * Parses the string into a JSON-Object (via GSON), creates an Event and enqueues the Event into the blocking queue.
     */
    public void run() {
        BufferedReader bufferedReader;
        String readPacket = null;
        System.out.println("New client.");
        while (true) {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                char[] buffer = new char[256];
                int bufferLength = bufferedReader.read(buffer, 0, 256);

                readPacket = new String(buffer, 0, bufferLength);

            } catch (SocketException e) {
                System.out.println("Client has disconnected.");
                clientLogger.info("Client has disconnected.");
                return;
            } catch (IOException e) {
                clientLogger.severe("An error occurred while trying to write data in the buffered reader.");
                e.printStackTrace();
            }
            Gson gson = new Gson();
            Event event = gson.fromJson(readPacket, Event.class);
            System.out.println("New event. ID: " + event.getID() + ", Type: " + event.getDirection());

            Server.enqueueEvent(event);
        }
    }
}