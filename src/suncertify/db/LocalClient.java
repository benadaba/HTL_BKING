/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import suncertify.util.Logger;
import java.util.logging.Level;

/**
 * Gives implementation to the methods which the client will call when booking
 * or searching for hotel rooms.
 *
 * @author Bernard
 */
public class LocalClient implements DBClient{

    /*ID used for interoperability */
   public static final long serialVersionUID = 1L;

   /*
    * Stores the result of all records returned from the database file as map
    * values with their associated record numbers as key values.
    * HashMap provides random and quick access to the records
    * Declared static to allow all instances to access and update a common map.
    *
    */
    private static Map<Long, HotelRoom> recordNumbers
                 = new HashMap<Long, HotelRoom>();


    /* Database access object */
    private  DBAccess data;

  /* Gets an instance of the db subsystem logger to log messages
   */
    private java.util.logging.Logger log=Logger.getLogger("db");

    /**
     * Constructor
     * @param dbLocation Location of database file
     * @throws FileNotFoundException if the specified file could not be found.
     */
    public LocalClient(String dbLocation) 
            throws FileNotFoundException {
        log.entering("LocalClient", "Constructor", dbLocation);
        data = new Data(dbLocation);  
       
        //retrieves the record numbers of
        //all non deleted records from the database
        long [] recordNos = data.findByCriteria(new String[7]);

        //Uses the retrieved numbers to
        //get the actual records from the database to be
        //store in a hash map used as local cached memory
         for(long i = 0; i< recordNos.length; i++){
              try {
                HotelRoom room = getHotelRoom(recordNos[(int)i]);
                if (room != null) {
                    long recNo = recordNos[(int)i];
                    recordNumbers.put(recNo, room);
                }
              } catch (RecordNotFoundException ex) {
                    log.log(Level.FINER, ex.getMessage(),ex);
              }
         }
        log.exiting("LocalClient", "Constructor");
    }


    /*
     * Converts database String values into hotel room objects
     *
     * @throws RecordNotFoundException if hotel room cannot be found using its
     *         associated record number in the database file.
     */
      private HotelRoom getHotelRoom(long recNo) throws RecordNotFoundException{
          log.entering("LocalDataBase","getHotelRoom", recNo);
          HotelRoom room=null;
          String allFields[]= data.readRecord(recNo);
          room=new HotelRoom(allFields[HotelRoom.NAME_FIELD],
                                allFields[HotelRoom.LOCATION_FIELD],
                                allFields[HotelRoom.SIZE_FIELD],
                                allFields[HotelRoom.SMOKING_FIELD],
                                allFields[HotelRoom.RATE_FIELD],
                                allFields[HotelRoom.DATE_FIELD]);
            room.setOwner(allFields[HotelRoom.OWNERID_FIELD]);
          log.exiting("LocalDataBase","getHotelRoom", room);
          return room;
      }

/**
 * Called when a client books a record and for that matter a hotel room.
 * 
 * @param owner the ownerID of the client booking the hotel room
 * @param room the hotel room to be booked
 * @return true if hotel room is booked successful, false if otherwise
 * @throws RecordNotFoundException if hotel room cannot be found using its
 *         associated record number in the database file.
 * @throws SecurityException if the client is not the one who locked the room
 * @throws IOException The IOException is declared in order to be able to throw
 * a RemoteException when running in networked mode.
 */
 public boolean bookHotelRoom(String owner, HotelRoom room)
        throws RecordNotFoundException, SecurityException, IOException{
           log.entering("LocalClient", "bookHotelRoom",
                   new Object[]{owner, room});
           if(owner==null){
                 throw new IllegalArgumentException("Null owner parameter");
           }
           if(room==null){
               throw new IllegalArgumentException("Null room parameter");
           }

           boolean booked=false;
            //check room availability in cached memory
           if(checkRoomAvailability(room)){
               //retrieves available room in cached memory
               for (Map.Entry<Long,HotelRoom> iter :recordNumbers.entrySet()){
                    HotelRoom cache=iter.getValue();
                    if(cache.equals(room)){
                        long recNo=iter.getKey();
                        HotelRoom comp=getHotelRoom(recNo);
                        if(comp!=cache){//cache record No needs to be updated 
                        //to latest
                          recordNumbers.put(recNo, comp);              
                        }
                        String newOwner=comp.getOwner();
                        if(!newOwner.isEmpty()){
                           //replace cached record new owner with latest record
                            //owner
                           recordNumbers.put(recNo, comp);
                           log.fine("New owner of record is "+newOwner);
                           break;
                        }
                       room.setOwner(owner);
                       long cookie = 0;
                       cookie=data.lockRecord(recNo);
                       data.updateRecord(recNo,room.getFields(),cookie);
                       data.unlockRecord(recNo, cookie);
                       recordNumbers.put(recNo, room);
                       booked=true;
                       break;
                    }
                }
            }
            log.exiting("LocalClient", "bookHotelRoom",booked);
            return booked;
      }

     /*
      * Returns the Hotel room list objects as a string array
      * 
      * @param rooms: A List containing hotel room objects
      * @return String[][] array containing the String representation
      * of Hotelroom objects
      */
     private  static  String[][] getArray(List<HotelRoom> rooms){
          int row=rooms.size();
          String[][] pSelf=new String[row][HotelRoom.TOTAL_FIELD];
          for(int i=0;i<row;i++) {
            pSelf[i] = rooms.get(i).getFields();
          }
          return pSelf;
    }

     /**
      * Returns all the hotel room objects loaded into the cache (HashMap)
      * @return Object[][]  array which contains the hotel room records' fields
      *                   values
      */
      public Object[][] getAllRooms(){
          log.entering("LocalDataBase","getAllRooms");            
          return getArray(new ArrayList<HotelRoom>(recordNumbers.values()));
      }


    /**
     * Called when the client wants to search for records by providing
     * a criteria
     * @param criteria the search criteria
     * @return Hotel room in the form of an array.
     * @throws IOException The IOException is declared in order to be able to
     * throw a RemoteException when running in networked mode.
     */
   public String[][] getHotelRoomsByCriteria(String[] criteria)
                            throws IOException{
        log.entering("LocalDataBase","getRoomsByCriteria",criteria);
        String info[][]=null;
        if(criteria==null){
           throw new IllegalArgumentException("Null criteria parameter");
        }
        if (criteria.length != HotelRoom.TOTAL_FIELD) {
            throw new IllegalArgumentException("criteria length is not  " +
                    "equal to rceord field length :" +HotelRoom.TOTAL_FIELD);
        }
        List<HotelRoom> list= new ArrayList<HotelRoom>();

        long index[]=data.findByCriteria(criteria);

        for(long nos:index){
            try {
                HotelRoom room = getHotelRoom(nos);
                if (room != null) {
                    recordNumbers.put(nos, room); //update the cache memory
                    list.add(room);
                }
            } catch (RecordNotFoundException ex) {
                log.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        info=getArray(list);
        log.exiting("LocalDataBase","getRoomsByCriteria",info);
        return info;
    }

    /**
     * Checks the availability of a hotel room to be booked.
     * This check is based on the ownerId of the hotel room and
     * 48 hours of the start of this room's occupancy ( 48 hour booking rule).
     * With 48 hour rule, it is assumed that all the record's time of start of
     * room occupancy is 00:00:00 BST. Hence any second or minute past this time
     * makes a difference.
     * It is also assumed that the client is booking a room at this current
     * System date and time, hence the current date and time is used for the
     * booking.
     * 
     * @param room the room to be booked
     * @return true if the is not already booked with an ownerId and the booking
     * time is within 48 hours of the start of the room occupancy,
     * false if otherwise.
     */
    public boolean checkRoomAvailability(HotelRoom room) {
        log.exiting("LocalClient", "checkRoomAvailability",room);
         
         if(room==null){
                throw new IllegalArgumentException("Null room parameter");
         }
        boolean available=false;
        if(room.getOwner().isEmpty()){
            for (Map.Entry<Long,HotelRoom> iter :recordNumbers.entrySet()) {
               HotelRoom cache=iter.getValue();
               if(cache.equals(room)){
                    if(!(cache.getOwner().isEmpty())){
                       log.fine("Cached Room is already booked");
                    }else{
                        Date recordDate = new Date(room.getDate());
                        Date currentDate=new Date();
                        long millisecs=currentDate.getTime()
                                -recordDate.getTime();
                        double day=((double)millisecs)/86400000;
                        if (day>=0  && day<=2){
                            available=true;
                        }
                        log.fine("Booking day range "+day+" days");
                    }
                    break;
               }
           }
        }
        log.exiting("LocalClient", "checkRoomAvailability",available);
        return available;
    }

}
