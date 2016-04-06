/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.db;

import java.io.IOException;
import java.io.Serializable;


/**
 * Interface to be called by the Client. Provides methods which will
 * be called in the GUI in either of the running modes; either through the
 * server or local connection.
 * 
 * @author Bernard
 */
public interface DBClient extends Serializable {


    /**
     * Called when a client books a record.
     * @param owner the ownerID of the client booking the hotel room
     * @param room the hotel room to be booked
     * @return true if booking was successful, false if otherwise.
     * @throws RecordNotFoundException if hotel room cannot be found using its
     *         associated record number in the database file.
     * @throws SecurityException if the client is not the one who locked the room
     * @throws IOException The IOException is declared in order to be able to
     * throw a RemoteException when running in networked mode. 
     */
    public boolean bookHotelRoom(String owner, HotelRoom room)
            throws RecordNotFoundException, SecurityException, IOException;

    /**
     * Called when the client wants to search for records using the name
     * and/or location as search criteria.
     * @param input the search criteria
     * @return Hotel room in the form of an array.
     * @throws IOException The IOException is declared in order to be able to
     * throw a RemoteException when running in networked mode.
     */
     public String[][] getHotelRoomsByCriteria(String[] input)throws IOException;

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
      * @throws IOException The IOException is declared in order to be able to
     * throw a RemoteException when running in networked mode.
     */
     public boolean checkRoomAvailability(HotelRoom room) throws IOException;

    /**
     * Returns all the hotel room objects loaded into the cache (HashMap)
     * @return Object[][]  array which contains the hotel room records' fields
     *                   values
     * @throws IOException The IOException is declared in order to be able to
     * throw a RemoteException when running in networked mode.
     */
    public Object[][] getAllRooms()throws IOException;

}
