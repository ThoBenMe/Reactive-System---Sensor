package de.thro.inf.reactive;

import de.thro.inf.NetworkConfiguration;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class ClientAcceptor.
 * Handles the data from the server (clients).
 *
 * @author Thomas Meza on 27.05.2019
 */
public class ClientAcceptor extends Thread {

    /**
     * Socket (server-side).
     */
    private ServerSocket serverSocket;

    /**
     * Constructor.
     * Creates a new server socket with the port and ip given from the networkConfiguration class.
     *
     * @param networkConfiguration contains the ip and port from the (network)Configuration-Class.
     */
    public ClientAcceptor(NetworkConfiguration networkConfiguration) {
        try {
            System.out.println("Using Port: " + networkConfiguration.getPort());
            System.out.println("IP: " + networkConfiguration.getInetAddress());
            serverSocket = new ServerSocket(networkConfiguration.getPort(), 0, networkConfiguration.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a new client in an extra thread.
     */
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Server.getExecutor().execute(new Client(socket));
            } catch (IOException e) {
                System.err.println("An error occurred while trying to get a connection or starting a new Client-Thread.");
                e.printStackTrace();
            }
        }
    }
}
