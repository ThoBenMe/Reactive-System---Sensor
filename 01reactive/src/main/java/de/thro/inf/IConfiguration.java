package de.thro.inf;

import java.net.InetAddress;

/**
 * Interface which provides the Configuration classes with the getInetAddress and the getPort method.
 *
 * @author Thomas Meza on 26.05.2019
 */
public interface IConfiguration {

    /**
     * Declaration of the getInetAddress method.
     */
    InetAddress getInetAddress();

    /**
     * Declaration of the getPort method.
     */
    int getPort();
}
