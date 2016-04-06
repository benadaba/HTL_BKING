/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import suncertify.util.Logger;

/**
 * Contains all the methods for the management and manipulating of the
 * database file.
 * Gives implementation to all the assignments interface methods.
 * Implements DBAccess and Serialiable.
 * Has one Map which holds the record numbers of locked records as a 
 * key of the map and the cookie which is generated when a record is
 * locked as the value of the map.
 *
 * @author Bernard
 */
public  class Data implements DBAccess {

 /*
  * Physical file on disk which contains the records.
  */
  private RandomAccessFile database;

  /*
   * Holds record numbers of locked records as keys and the cookie generated
   * when a record is locked as the corresponding value.
   * if not static then lock and unlock will not work
   */
  private static Map<Long, Long> lockCookies = new HashMap<Long,Long>();

  /*
   * For locking and unlocking processes when performing an atomic operation.
   * Used instead of the implicit monitor lock accessed using
   * synchronized methods and statements
   */
  private static Lock recordLock  = new ReentrantLock();

  /* For thread-safety read and write operations */
  private static ReadWriteLock databaseLock  = new ReentrantReadWriteLock();

  /*
   * Used with the Lock to replace the use of the Object monitor methods of wait,
   * notify and notifyAll
   */
  private static Condition condition = recordLock.newCondition();

  /*
   * Used when calculating time for which a Thread will have to wait until
   * a locked record is unlocked.
   */
  private static final int TIMEOUT = 3000;

  /* The start of the records in the database file */
  private final static int FILE_OFFSET=56;

  /* Location of database file     */
  private  static String dbLocation ;

 /*
  *Field length indicating the status of the record;
  */
  private static final int FLAG_LENGTH = 1;

 /*
  * Indicates that the record is valid
  */
  private static final int VALID_RECORD = 00;

 /*
  * Indicates that the record is deleted
  */
  private static final int DELETED_RECORD = 0xFF;

 /*
  * The main Logger instance for this class. All log messages from
  * this class are logged through this member.
  * The Logger namespace is <code>gui</code>.
  */
  private static java.util.logging.Logger log = Logger.getLogger( "db" );

    /**
     *This constructor initialises the location of the database file
     * @param givenDbLocation String which specifies the location of the
     * database file.
     * @throws FileNotFoundException if the database file cannot be found
     */
   public Data(String givenDbLocation)throws FileNotFoundException{
       log.entering("Data", "Constructor", givenDbLocation);
       dbLocation = givenDbLocation;
       database = new RandomAccessFile(givenDbLocation, "rws");
       log.info("opened database file at location :" +dbLocation);
       log.exiting("Data", "Constructor");
    }

   /**
     * Reads a record from the file.
     * Returns an array where each element is a record value.
     * @param recNo number of the record in the file in terms of ordering
     * @return String array which contains this record's values
     * @throws suncertify.db.RecordNotFoundException if record is marked as
     * deleted or does not exist in the database file
     */
    public String[] readRecord(long recNo) throws RecordNotFoundException {
         log.entering("Data", "readRecord", recNo);
         databaseLock.readLock().lock();
         String[] record =null;
         try {
                final byte[] input = new byte[HotelRoom.RECORD_LENGTH];
                long recordPosition = fileOffset(recNo);
                record= new String[HotelRoom.TOTAL_FIELD];

                synchronized(database){

                    database.seek(recordPosition);
                     int flag=database.read();
                     if(flag==DELETED_RECORD) {
                        throw new RecordNotFoundException("Record Marked as " +
                                                            "Deleted");
                    }
                    database.readFully(input);
                }
            /*
            * Converts Byte and store them into a String array
            */
            class RecordFieldReader {
                private int offset = 0;
             /*
             * converts the required number of bytes into a String.
             *
             * @param length the length to be converted from current offset.
             * @return the converted String
             * @throws UnsupportedEncodingException if "US-ASCII" not known.
             */
                 String read(int length) throws UnsupportedEncodingException {
                    String str = new String(input, offset, length,"US-ASCII");
                    offset += length;
                    return str.trim();
                }
            }
            RecordFieldReader reader=new RecordFieldReader();

            record[HotelRoom.NAME_FIELD] =
                    reader.read(HotelRoom.NAME_LENGTH);
            record[HotelRoom.LOCATION_FIELD]=
                    reader.read(HotelRoom.LOCATION_LENGTH);
            record[HotelRoom.SIZE_FIELD]=
                    reader.read(HotelRoom.SIZE_LENGTH);
            record[HotelRoom.SMOKING_FIELD] =
                    reader.read(HotelRoom.SMOKING_LENGTH);
            record[HotelRoom.RATE_FIELD]=
                    reader.read(HotelRoom.RATE_LENGTH);
            record[HotelRoom.DATE_FIELD] =
                    reader.read(HotelRoom.DATE_LENGTH);
            record[HotelRoom.OWNERID_FIELD] =
                    reader.read(HotelRoom.OWNERID_LENGTH);


        } catch (IOException ex) {
            log.log(Level.FINER,ex.getMessage(),ex );
            //The IOException is wrapped in the RecordNotFoundException in
            // order to be able to throw the exception when an error occurs
            // while reading from the dababase file
            RecordNotFoundException e= new RecordNotFoundException();
            e.initCause(ex);
            throw e;
        }finally{
            databaseLock.readLock().unlock();
        }
       log.exiting("Data", "readRecord",record);
       return (record);
    }



     /*
      * used to convert String to byte[] array that will be used to store
      data and into the database Class which assists in converting Strings to a
      * byte[] with its write() method and back to String with the StringBuilder
      * object 
      */
    private static class RecordFieldWriter {

        /* current position in byte[] */
        private int offset = 0;
        final StringBuilder out =
                new StringBuilder(
                            new String(
                                new byte[HotelRoom.RECORD_LENGTH]));
        /*
         * converts a String of specified length to byte[]
         *
         * @param data the String to be converted into part
         * of the byte[].
         * @param length the maximum size of the String
         */
         void write(String data, int length) {
             out.replace(offset,offset+ data.length(),data);
             offset+= length;
        }
    }

    /*
     * Gets the data from the passed String[] array, and the bytes gotten from
     * the data returned as one String to be later written
     * appropriately to the database file.
     *
     * @param data String[] which contains the data to be converted to String
     * @return String containing all the information in the String[] elements
     */
    private static String getString(String[] data){
        RecordFieldWriter writer = new RecordFieldWriter();

        writer.write(data[HotelRoom.NAME_FIELD],
                HotelRoom.NAME_LENGTH);
        writer.write(data[HotelRoom.LOCATION_FIELD],
                HotelRoom.LOCATION_LENGTH);
        writer.write(data[HotelRoom.SIZE_FIELD],
                HotelRoom.SIZE_LENGTH);
        writer.write(data[HotelRoom.SMOKING_FIELD],
                HotelRoom.SMOKING_LENGTH);
        writer.write(data[HotelRoom.RATE_FIELD],
                HotelRoom.RATE_LENGTH);
        writer.write(data[HotelRoom.DATE_FIELD],
                HotelRoom.DATE_LENGTH);
        writer.write(data[HotelRoom.OWNERID_FIELD],
                HotelRoom.OWNERID_LENGTH);
        return writer.out.toString();
    }

      /*
       * Performs the writing of bytes' operations to the database file.
       * Does this by using it's write method
       * Takes String values and converts them to bytes by writing into the
       * appropriate record number location
       * Used by the create and update methods when writing to the databasefile.
       * @param recNo database location number of the record to be written to.
       * @param data the data to write to file.
       * @throws IOException if error occurred while doing
       * the writing operation.
       * @throws RecordNotFoundException if record is deleted or
       *              cannot be found
       */
      private int writeToFile(long recNo, String[] data)
              throws RecordNotFoundException, IOException{
          log.entering("Data", "writeBytes", new Object[]{recNo, data});
          int length=0;
          databaseLock.writeLock().lock();
          long positionInFile=fileOffset(recNo);
          try {
                String info=getString(data);
                synchronized (database) {
                    database.seek(positionInFile + FLAG_LENGTH);
                    database.write(info.getBytes("US-ASCII"));
                }
                log.fine("Record "+ recNo+ ": written to the database is:"+info);
                length=info.length();
          } finally {
              databaseLock.writeLock().unlock();
          }
          log.exiting("Data", "writeBytes",length);
          return length;
    }

    /*
     * Returns the last record number in the database file
     *
     * @return long value of the last record number
     * @throws java.io.IOException if an exception occurs while operating on
     *       the database.
     */
    private long lastRecNo() throws IOException {
        log.entering("Data", "lastRecNo");
        long recordNo =
                (database.length()-FILE_OFFSET)/
                 ( HotelRoom.RECORD_LENGTH + FLAG_LENGTH);
        log.exiting("Data", "lastRecNo", recordNo);
        return (recordNo);
    }

    /*
    * Calculates the offset of a record in the database file for writing or
    * reading that record. In calculating the offset, the status flag length is
    * added to the total record length to get the entire space/size occupied
    * by the actual record and its status flag.
    * This methods also checks the validity of the record number, that is
    *`whether or not it is within the range of record numbers in the database
    * file
    * @param database dabasefile of all the records
    * @param recNo record number of the record which offset is calcutated
    * @return position or offset of the record in the database file.
    * @throws suncertify.db.RecordNotFoundException if record number
        does not exist or end of EOFException is reached.
    */
  private long fileOffset(long recNo)
          throws RecordNotFoundException, IOException{
        log.entering("Data", "offset", recNo);
        long recordPosition=0;
        if(recNo <1||recNo >lastRecNo()){
            throw new RecordNotFoundException();
        }
        recordPosition=(HotelRoom.RECORD_LENGTH + FLAG_LENGTH)*
                        (recNo-1) +FILE_OFFSET;
        log.exiting("Data", "offset", recordPosition);
        return (recordPosition);
  }


    /**
     * Modifies the fields of a record. The new value for field n appears in
     * data[n]. 
     * @param recNo database file location number of the record to be updated
     * @param data the data to update the record with.
     * @param lockCookie cookie returned when this record was locked
     * @throws suncertify.db.RecordNotFoundException if record is deleted or
     *              cannot be found
     * @throws java.lang.SecurityException if the record is locked with a cookie
     *         other than lockCookie.
     */
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException {
        log.entering("Data","updateRecord",new Object[]{recNo,data,lockCookie});
        try {           
               fileOffset(recNo);
               if (lockCookies.get(recNo) != lockCookie) {
                throw new SecurityException("Security broken Exception");
            }

               readRecord(recNo); //check if record  has been deleted
               int writelength=writeToFile(recNo,data);
               log.fine("Length written to File :"+writelength);
        } catch (UnsupportedEncodingException ex) {
            log.log(Level.FINER, ex.getMessage(), ex);
        } catch (IOException ex) {
            log.log(Level.FINER,ex.getMessage(), ex);
        }
        log.exiting("Data","updateRecord");
    }

    /**
     * Deletes a record, making the record number and associated disk storage
     * available for reuse.
     * A record which is already deleted cannot be re-deleted, so before a
     * record is deleted that record is read from the database again to check 
     * whether or not it is deleted.
     * @param recNo location number of the record in the file
     * @param lockCookie cookie generated when this record was locked
     * @throws suncertify.db.RecordNotFoundException if record cannot
     * be found in file
     * @throws java.lang.SecurityException if the record is locked with a cookie
     * other than lockCookie.
     */
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException {
        log.entering("Data","deleteRecord",new Object[]{ recNo,lockCookie});
        databaseLock.writeLock().lock();
        try {
            readRecord(recNo); //check if record is already deleted
            if (lockCookies.get(recNo) != lockCookie) {
                throw new SecurityException("Security broken Exception");
            }
           
             synchronized (database) {
                  database.seek(fileOffset(recNo));
                  database.write(DELETED_RECORD);
             }     
        }catch (IOException ex){
            log.log(Level.FINER, ex.getMessage(), ex);
        }finally{
            databaseLock.writeLock().unlock();
        }
        log.exiting("Data","deleteRecord");
    }

     /**
     * Returns an array of record numbers that match the specified criteria.
     * Field n in the database file is described by criteria[n].
     * A null value in criteria[n] matches any field value.
     * A non-null value in criteria[n] matches any field value that begins with
     * criteria[n].
     * (For example, "Fred" matches "Fred" or "Freddy".)
     * @param criteria specified search criteria
     * @return record numbers that matched the search criteria
     */
  public long[] findByCriteria(String[] criteria) {
        log.entering("Data","findByCriteria",criteria);
        Collection<Long> foundList = new ArrayList<Long>();

        if (criteria == null) {
            throw new IllegalArgumentException("Null Criteria parameter");
        }
        if (criteria.length != HotelRoom.TOTAL_FIELD) {
            throw new IllegalArgumentException("Criteria length is not equal" +
                    " to rceord field length :" +HotelRoom.TOTAL_FIELD);
        }

        try{
            long last=lastRecNo();
            for (long recNo = 1; recNo <= last; ++recNo) {
                try {
                    String record[]= readRecord(recNo);
                    boolean found=false;
                    for (int i = 0; i < HotelRoom.TOTAL_FIELD; ++i) {
                         if (criteria[i] == null) {
                              criteria[i]="";
                         }
                         /*
                          * Converts both the search criteria and the read
                          * record's values to lowercase for uniform
                          * comparison
                          */
                         criteria[i]=criteria[i].toLowerCase();
                         record[i]=record[i].toLowerCase();
                         if(!record[i].startsWith(criteria[i])){
                             found=false;
                             break;
                          }
                          found=true;
                    }
                    if( found ) {
                        foundList.add(recNo);
                    }
                }catch(RecordNotFoundException ex){
                    log.finer("Record "+recNo+" is deleted");
                }
            }
        } catch (IOException ex) {
            log.log(Level.FINER, ex.getMessage(), ex);
        }

        long[] result=getLong(foundList.toArray(new Long[0]));
        log.exiting("Data","findByCriteria",result);
        return result;
    }

  /*
   * Gets a long[] of the primitive long type from Long[] array of the Long
   * object type. This method is primarily used in the findByCriteria() method
   * to obtain a long[] containing record number(s) returned from the search
   * @param array Long[] from which to obtain the long[] of the primitive
                long type
   * @return long[] which contains elements of passed Long[]
   */
   private static long[]getLong(Long[] array){
       long[] result = new long[array.length];
       for (int i=0;i<array.length;i++ ){
           result[i]=array[i];
       }
       return result;
   }

   /**
    * Creates a new record in the database (possibly reusing a deleted entry).
    * If IllegalArgumentException is thrown from the create method, it either
    * means that the passed String[] array does not meet the required length or
    * an invalid data (null) was passed into the function hence there would not
    * be any need to go ahead and create an invalid record.
    * It is assumed that the client will pass in String[] array which contains
    * valid data in its elements and not null values. Hence no checks has been 
    * done on this. This can however be improved in the future though.
    * When the end of file is reached without finding a
    * deleted entry, the new record is written to the end of the database file.
    * @param data the given data to be inserted and created
    * @return record number of the newly created record.
    * @throws suncertify.db.DuplicateKeyException if the record to be created
    * already exists in the database file.
    */
    public long createRecord(String[] data) throws DuplicateKeyException {
        log.entering("Data","createRecord",data);
        if ( data == null){
            throw new IllegalArgumentException("Null criteria parameter");
        }
        if( data.length!= HotelRoom.TOTAL_FIELD){
          throw new IllegalArgumentException("Criteria length is " +
                    "not equal to rceord field length :"
                    +HotelRoom.TOTAL_FIELD);
        }
        databaseLock.writeLock().lock();
        long recNo= 1;
        try {
            if(findByCriteria(data).length!=0) {
                throw new DuplicateKeyException();
            }
            String info=getString(data);
            long last =  lastRecNo();
            long offset =0;
            boolean deleted=false;
            for( recNo = 1;  recNo <= last; recNo++ ){
                try {
                     offset=  fileOffset(recNo);
                     readRecord(recNo);//check for deleted entry
                } catch (RecordNotFoundException ex){
                    log.finer("A deleted entry found at record" +
                            " position" + recNo + ", a new record" +
                            " is being created in its stead");
                    deleted=true;
                    break;
                }
            }
            synchronized(database){
                if (deleted){//seeking deleted entry
                    database.seek(offset);
                }else{//seeking end of database file
                   recNo=last+1;
                   long oldLength = database.length();
                   database.seek(oldLength );
                }
                database.writeByte(VALID_RECORD);
                database.write(info.getBytes("US-ASCII"));
            }
            log.fine("Record"+ recNo+
                    " written to the database is:"+info);
        } catch (UnsupportedEncodingException ex) {
            log.log(Level.FINER, ex.getMessage(), ex);
        } catch (IOException ex) {
            log.log(Level.FINER, ex.getMessage(), ex);
        } finally {
          databaseLock.writeLock().unlock();
        }
        log.exiting("Data","createRecord",recNo);
        return recNo;
    }

    /**
     * Locks a record so that it can only be updated or deleted by this client.
     * Returned value is a cookie that must be used when the record is unlocked,
     * updated, or deleted. If the specified record is already locked
     * by a different client, the current thread gives up the CPU and consumes
     * no
     * CPU cycles until the record is unlocked.
     * @param recNo location number of the record to be locked
     * @return a long ookie that must be used when the record is unlocked,
     *        updated or deleted
     * @throws suncertify.db.RecordNotFoundException if the record does not
     * exist
     *         or marked as  deleted in the database file.
     */
    //RecNo starts at 1
    public long lockRecord(long recNo) throws RecordNotFoundException {
        log.entering("Data","lockRecord",recNo);
        long cookie=0;
        recordLock.lock();
        try {
          
            fileOffset(recNo);           
            while (lockCookies.containsKey(recNo)) {
                long endTimeMSec = System.currentTimeMillis() + TIMEOUT;
                long timeLeftMSec = endTimeMSec - System.currentTimeMillis();

                  log.fine("record :" + recNo + " is already locked so"
                        + Thread.currentThread().getName() + "is waiting"
                        + "for at most "+ timeLeftMSec + " milliseconds");
                condition .await(timeLeftMSec, TimeUnit.MILLISECONDS);
            }

            //generate cookie.
            // System.currentTimeMillis generally has a very fast response time
            //combined with Random().nextLong() provides a more unique cookie.
            cookie =System.currentTimeMillis()*new Random().nextLong();
            log.fine("lockCookie " + cookie + " is generated");
            readRecord(recNo); //check if record has been already deleted
            lockCookies.put(recNo, cookie);
            log.fine("Thread " + Thread.currentThread().getName()
                        + "got Lock for " + recNo);
            log.fine("Locked record count = " + lockCookies.size());
        }catch (IOException ex) {
           log.log(Level.FINER, ex.getMessage(), ex);
        }catch(InterruptedException ex){
            log.finer("waiting time has elapsed and record " + recNo
                     + " is still not released so " +
                     Thread.currentThread().getName()+ " is being interrupted");

                RecordNotFoundException e=
                    new RecordNotFoundException(Thread.currentThread().
                    getName() + " was interrupted while trying to lock" +
                    "record " + recNo);
                e.initCause(ex);
                throw e;
        }finally {
            recordLock.unlock();
        }
        log.exiting("Data", "lockRecord",cookie);
        return cookie;
    }
    /**
     * Releases the lock on a record. Cookie must be the cookie returned when
     * the record was locked; otherwise throws SecurityException.
     * @param recNo Record Number
     * @param cookie  Lock Cookie; cookie returned when this record was locked
     * @throws java.lang.SecurityException if cookie is not the same as cookie
     *            which was returned when the record was locked
     */
    public void unlockRecord(long recNo, long cookie) throws SecurityException{
        log.entering("Data","unlock",new Object[]{recNo,cookie});
        recordLock.lock();
        try{
            if(lockCookies.containsKey(recNo)){
                if (lockCookies.get(recNo) == cookie) {
                     lockCookies.remove(recNo);
                     condition.signal();
                }else{
                    throw new SecurityException("Security broken Exception");
                }
            }else{
                throw new SecurityException("Record is not locked, hence it" +
                        "cannot be unlocked");
            }
        }finally{
            recordLock.unlock();
        }
        log.exiting("Data","unlockRecord");
    }
    
}
