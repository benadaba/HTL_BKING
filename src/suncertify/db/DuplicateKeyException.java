/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.db;

/**
 * Exception thrown when the record to be inserted into the database file already
 * exits
 * 
 * @author Bernard
 */

public class DuplicateKeyException extends Exception {

    /* Default message to be displayed when this Exception is thrown.*/
   private static String message= "record already exists!";
    
    /**
     * No argument constructor which displays a message when this exception is
     * thrown.
     */
    public DuplicateKeyException(){
        super(message);
    }

    /**
     * Constructor which takes a custom message to be printed out when this
     * exception is thrown
     * @param msg custom message
     */
    public DuplicateKeyException(String msg){
        super(msg);
    }
}
