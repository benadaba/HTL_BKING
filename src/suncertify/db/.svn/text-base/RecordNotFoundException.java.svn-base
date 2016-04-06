/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.db;

/**
 * Thrown when a specified record is marked as deleted or does not exist in the
 * database file
 * 
 * @author Bernard
 */
public class RecordNotFoundException extends Exception{

    /* Default message displayed when this exception is thrown */
    private static final String message = "Such record does not exist";

    /**
     * No argument constructor which displays a message when this exception is
     * thrown.
     */
    public RecordNotFoundException() {
        super(message);
    }

    /**
     * Constructor which takes a custom message to be printed out when this
     * exception is thrown
     * @param msg custom message
     */
    public RecordNotFoundException(String msg) {
          super(msg);
    }
}
