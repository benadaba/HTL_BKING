/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.server;

import java.rmi.Remote;
import suncertify.db.DBClient;

/**
 *The remote interface for the GUI-Client.
 *It has all the interface methods to be called by the 
 * client when running in the networked mode.
 *
 * @author Bernard
 */
public interface RemoteDatabase extends Remote, DBClient {


}
