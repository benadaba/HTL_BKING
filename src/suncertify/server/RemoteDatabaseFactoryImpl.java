/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.server;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import suncertify.util.Logger;


/**
 * Gives implementation to the factory, RemoteDatabaseFactory
 *
 * @author Bernard
 */
public class RemoteDatabaseFactoryImpl extends UnicastRemoteObject 
        implements RemoteDatabaseFactory {

     /**
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>server</code>.
     */
  private static  java.util.logging.Logger log = Logger.getLogger( "server" );

   /* location of the database file */
   private String dbPath ;

   /**
    * Constructor which initialises the database location.
    * @param dbPath location of the database file
    * @throws java.rmi.RemoteException if registry could not be contacted
    */
     RemoteDatabaseFactoryImpl(String dbPath) throws RemoteException{
        log.entering(" RemoteDatabaseFactoryImpl", "Constructor", dbPath);
         this.dbPath  = dbPath ;
         log.exiting(" RemoteDatabaseFactoryImpl", "Constructor");

     }

     /**
      * Called to obtain reference (stub) to the remote object, RemoteDatabase
      * @return RemoteDatabase reference (stub) to the remote object
      * @throws java.rmi.RemoteException if registry could not be contacted
      * @throws FileNotFoundException if the specified file cannot be found
      */
     public RemoteDatabase getClient() 
             throws RemoteException,FileNotFoundException {
        log.entering(" RemoteDatabaseFactoryImpl", "getClient");

        RemoteDatabaseImpl remoteDb = null; 
        try{  
          if(remoteDb==null){
            remoteDb = new RemoteDatabaseImpl(dbPath);
            }
        }catch(FileNotFoundException ex){
            log.log(Level.FINER, "The specified file could not be found", ex);
            throw ex;
        }
      log.exiting(" RemoteDatabaseFactoryImpl", "getClient");
      return  remoteDb ;
    }

}
