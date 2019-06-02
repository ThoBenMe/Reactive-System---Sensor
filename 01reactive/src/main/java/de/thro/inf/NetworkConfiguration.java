package de.thro.inf;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * Class NetworkConfiguration.
 * Allows configuration of third parties.
 */
public class NetworkConfiguration implements IConfiguration {

    /**
     * Contains the port number.
     */
    private int port;
    /**
     * Contains the user's parameters.
     */
    protected final String[] args;
    /**
     * Variable for the inetAddress.
     */
    private InetAddress inetAddress;
    /**
     * Static logger for the NetworkConfiguration class.
     */
    private static final Logger log = Logger.getLogger(NetworkConfiguration.class.getName());

    /**
     * NetworkConfiguration constructor.
     * Assigns the user's arguments to this class' arguments.
     * Sets the inetAddress to null and the port number to -1 as default values.
     *
     * @param args the user's arguments.
     */
    public NetworkConfiguration(String[] args) {
        this.args = args;
        inetAddress = null;
        port = -1;
    }

    /**
     * Assigns a port value to the port.
     * If the inetAddress is still null, inetAddress' value is the localhost (default).
     *
     * @return inetAddress.
     */
    @Override
    public InetAddress getInetAddress() {
        if (inetAddress == null) {
            try {
                if (args == null || args.length < 2) {
                    inetAddress = InetAddress.getLocalHost();
                    log.info("Local host is used on default.");
                } else {
                    inetAddress = InetAddress.getByName(args[0]);
                    log.info("inetAddress can be parsed.");
                }
            } catch (UnknownHostException e) {
                log.severe("An error occurred while trying to assign a host to the inetAddress.");
                e.printStackTrace();
            }
        }
        return inetAddress;
    }

    /**
     * Assigns a value to the port.
     *
     * @return the current port.
     */
    @Override
    public int getPort() {
        if (port < 0 || port > 65535) {
            try {
                if (args == null || args.length < 2) {
                    port = 36636;
                    log.info("Default port is used. Using Port: " + port);
                } else {
                    port = Integer.parseInt(args[1]);
                    log.info("Port can be parsed.");
                }
            } catch (Exception e) {
                log.severe("An error occurred while trying to set a value to the port.");
                e.printStackTrace();
            }
        }
        return port;
    }

    /**
     * Creates a string which contains the inetAddress as well as the port number.
     *
     * @return string.
     */
    @Override
    public String toString(){
        if(inetAddress == null){
            return "";
        }
        return inetAddress.getHostAddress() + ":" + port;
    }

}

