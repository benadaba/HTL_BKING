

package suncertify.db;

/**
 * The required interface provided by Sun.
 * Has the methods for accessing the database file.
 *
 * @author Bernard
 */
public interface DBAccess {

     /**
     * Reads a record from the file.
     * Returns an array where each element is a record value.
     * @param recNo number of the record in the file in terms of ordering
     * @return String array which contains this record's values
     * @throws suncertify.db.RecordNotFoundException if the specified record does
      * not exist or marked as deleted in the database file.
     */
    public String[] readRecord(long recNo) throws RecordNotFoundException;

    /**
     * Modifies the fields of a record. The new value for field n appears in data[n].
     *
     * @param recNo database file location number of the record to be updated
     * @param data the data to update the record with.
     * @param lockCookie cookie returned when this record was locked
     * @throws suncertify.db.RecordNotFoundException if the specified record does
      *                     not exist or marked as deleted in the database file.
     * @throws java.lang.SecurityException if the record is locked with a cookie
     *         other than lockCookie.
     */
    public void updateRecord(long recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException;


    /**
     *Deletes a record, making the record number and associated disk storage
     * available for reuse.
     *
     * @param recNo location number of the record in the file
     * @param lockCookie cookie generated when this record was locked
     * @throws suncertify.db.RecordNotFoundException if record cannot be found in
     *    file
     * @throws java.lang.SecurityException if the record is locked with a cookie
     * other than lockCookie.
     */
    public void deleteRecord(long recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException;


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
    public long[] findByCriteria(String[] criteria);

   /**
    * Creates a new record in the database (possibly reusing a deleted entry).
    * Inserts the given data, and returns the record number of the new record.
    *
    * @param data the given data to be inserted and created
    * @return record number of the newly created record.
    * @throws suncertify.db.DuplicateKeyException if the record to be created to
    * be created already exists in the database file.
    *
    */
    public long createRecord(String[] data) throws DuplicateKeyException;

    
     /**
     * Locks a record so that it can only be updated or deleted by this client.
     * Returned value is a cookie that must be used when the record is unlocked,
     * updated, or deleted. If the specified record is already locked
     * by a different client, the current thread gives up the CPU and consumes no
     * CPU cycles until the record is unlocked.
     * @param recNo location number of the record to be locked
     * @return a long ookie that must be used when the record is unlocked,
     *        updated or deleted
     * @throws suncertify.db.RecordNotFoundException if the record does not exist
       *or marked as  deleted in the database file.
     */
    public long lockRecord(long recNo) throws RecordNotFoundException;


    /**
     * Releases the lock on a record. Cookie must be the cookie returned when
     * the record was locked; otherwise throws SecurityException.
     * @param recNo Record Number
     * @param cookie  Lock Cookie; cookie returned when this record was locked
     * @throws java.lang.SecurityException if cookie is not the same as cookie
     *            which was returned when the record was locked
     */
    public void unlockRecord(long recNo, long cookie) throws SecurityException;

}
