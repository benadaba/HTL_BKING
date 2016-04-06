/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.server;

import suncertify.util.Logger;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.*;

/**
 * Class for obtaining reference to the remote object.
 * Uses a Factory Pattern so that only its method can be
 * called without actually instantiating the class.
 * @author Bernard
 */
public abstract class RemoteConnector {


    /*
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>server</code>.
     */
  private static  java.util.logging.Logger log = Logger.getLogger( "server" );


    /**
     * Called to obtain the reference to the remote object, RemoteDatabase .
     * This method is declared static to give a strong reference to the reference
     * object which is returned so that the RMI garbage collector does not
     * clear the reference to the object after a long idleness.
     * 
     * @param hostName name(in URL format) associated with remote object
     *                  reference returned
     * @return RemoteDatabase a reference ( a stub ) for the remote object
     * @throws java.rmi.NotBoundException if the hostName is
     *                  not currently bound
     * @throws java.net.MalformedURLException if the hostName is not appropriately
     *                      formatted
     * @throws java.rmi.RemoteException if registry could not be contacted
     * @throws FileNotFoundException if the specified file could not be found.
     */
    public static RemoteDatabase  getRemote(String hostName)
            throws NotBoundException, MalformedURLException,
            RemoteException, FileNotFoundException {

        log.entering("RemoteConnector", "getRemote", hostName);

        String serverURL = "rmi://"+ hostName+
                "/HotelRoomServer";
        RemoteDatabaseFactory dataSerInt =
        (RemoteDatabaseFactory)Naming.lookup(serverURL);

        log.exiting("RemoteConnector", "getRemote",dataSerInt.getClient());

        return dataSerInt.getClient();
  }
}