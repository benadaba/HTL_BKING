/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import suncertify.db.DBClient;
import suncertify.db.HotelRoom;
import suncertify.db.LocalClient;
import suncertify.db.RecordNotFoundException;
import suncertify.util.Logger;




/**
 * Implements all the methods of the remote object.
 * That is all the methods calleable by the GUI - client
 * when running in networked mode; same as the ones in DBClient
 *
 * @author Bernard
 */
public class RemoteDatabaseImpl extends UnicastRemoteObject
        implements RemoteDatabase {

    /* Handle to the DBClient*/
    private DBClient client;

     /**
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>server</code>.
     */
  private static java.util.logging.Logger log = Logger.getLogger( "server" );


    /**
     * Constructor which instantiates the DBClient. The remote object, 
     * RemoteDatabase, is a type of it.
     * @param dbPath the location of the database file.
     * @throws java.rmi.RemoteException if registry could not be contacted
     * @throws FileNotFoundException if specified file cannot be found.
     */
    public RemoteDatabaseImpl(String dbPath)throws RemoteException,
            FileNotFoundException {
        super();
       log.entering(" RemoteDatabaseImpl", "Constructor",dbPath );

        client = new LocalClient(dbPath);

        log.exiting(" RemoteDatabaseImpl", "Constructor");
    }

    /**
     * Called when the client wants to search for records using the name
     * and/or location as search criteria.
     * @param input the search criteria
     * @return Hotel room in the form of String array.
     * @throws RemoteException if registry could not be contacted
     * @throws IOException if network connection is no longer existent
     */
@Override
    public String[][] getHotelRoomsByCriteria(String[] input)
            throws RemoteException, IOException {
        log.entering(" RemoteDatabaseImpl", "getHotelRoomsByCriteria",input );
        log.exiting(" RemoteDatabaseImpl", "getHotelRoomsByCriteria");

        return client.getHotelRoomsByCriteria(input);
    }

     /**
     * Called when a client books a record.
     * @param owner the ownerID of the client booking the record (hotel room)
     * @param room the hotel room to be booked
     * @return true if booking was successful, false if otherwise
     * @throws RemoteException if registry could not contacted.
     * @throws RecordNotFoundException if hotel room cannot be found using its
     *         associated record number in the database file.
     * @throws SecurityException if the client is not the one who locked the room
     * @throws IOException declared in order to be able to throw a
      * RemoteException when running in networked mode).
     */
@Override
    public boolean bookHotelRoom(String owner, HotelRoom room)
            throws RemoteException, RecordNotFoundException, IOException {
        log.entering(" RemoteDatabaseImpl", "bookHotelRoom",
                new Object[]{owner, room.toString()} );
        boolean booked=client.bookHotelRoom(owner, room);
        log.exiting(" RemoteDatabaseImpl", "bookHotelRoom",booked);
        return booked;
    }

 /**
     * Checks the availability of a hotel room to be booked.
     * This check is based on the ownerId of the hotel room and on
     * 48 hours of the start of this room's occupancy ( 48 hour booking rule).
     * With 48 hour rule, it is assumed that all the record's time of start of
     * room occupancy is 00:00:00 BST. Hence any second or minute past this time
     * makes a difference.
     * It is also assumed that the client is booking a room at this current
     * System date and time, hence the current date and time is used for the
     * booking.
     *
     * @param room the room to be booked
     * @return true if the room is not already booked with an ownerId and the booking
     * time is within 48 hours of the start of the room occupancy, false if
     * otherwise.
     * @throws RemoteException if registry could not be contacted
     * @throws IOException declared in order to be able to throw a
     *   RemoteException when running in networked mode.
     */
@Override
    public boolean checkRoomAvailability(HotelRoom room)
            throws RemoteException, IOException  {
         log.entering(" RemoteDatabaseImpl", "checkRoomAvailability",room);
         log.exiting(" RemoteDatabaseImpl", "checkRoomAvailability");
         
        return client.checkRoomAvailability(room);
    }

    /**
     * Returns all the hotel room objects loaded into the cache (HashMap)
     * @return Object[][]  array which contains the hotel room records' fields
     *                   values
     * @throws IOException The IOException is declared in order to be able to
     * throw a RemoteException when running in networked mode.
     */
    public Object[][] getAllRooms() throws IOException {
        log.entering(" RemoteDatabaseImpl", "getAllRooms");
        return client.getAllRooms();
    }

}
