/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.server;

import java.net.MalformedURLException;
import java.rmi.*;
import suncertify.util.Logger;
import java.rmi.registry.Registry;


/**
 * Registers and unregisters the RMI server
 *
 * @author Bernard
 */
public abstract class RegisterDatabase {

    /* checks if the server is already connected */
    private static  boolean isServerOn = false;


    /**
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>server</code>.
     */
  private static java.util.logging.Logger log = Logger.getLogger( "server" );


    /**
     * Starts the RMI  registry and binds it to the default port.
     * If server is already started, an exception is thrown and message is shown
     * to the user.
     * @param dbPath path location of the database file.
     * @throws RemoteException if the registry could not be exported or contacted
     * @throws MalformedURLException  if the name is not an appropriately 
     *      formatted 
     */
    public static void register( String dbPath )
            throws RemoteException, MalformedURLException {
         log.entering("RegisterDatabase", "register",dbPath);
         if (!isServerOn) {
                java.rmi.registry.LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                log.fine("HotelRoomServer Started ....");
                RemoteDatabaseFactoryImpl hotRoSerImp = new RemoteDatabaseFactoryImpl(dbPath);
                Naming.rebind("HotelRoomServer", hotRoSerImp);
                log.fine("HotelRoomServer running ....");
                isServerOn = true;
         }
         log.exiting("RegisterDatabase", "register");
     }

     /**
      * Unbinds the server from the RMI registry.
      * 
      * @throws Exception thrown when there is an exception in the course of
      * unbinding from the registry.
      */
     public static void unregister()throws Exception{
        log.entering("RegisterDatabase", "unregister");
        
            java.rmi.registry.LocateRegistry.getRegistry().unbind("HotelRoomServer");
            log.fine("HotelRoomServer stopped!");
            
        log.exiting("RegisterDatabase", "unregister");
     }
}
