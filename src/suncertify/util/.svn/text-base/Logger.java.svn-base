/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * Instance of this class is used to appropriately log all the essential
 * activities of the Application.
 * It uses the Singleton Pattern, hence only one instance of this class is
 * created whenever it is going to be used in any package.
 *
 * @author Bernard
 */
public class Logger {

    /* The directory to log into */
    private static final String  LOG_DIR="code/log/";

    /* The global logManager */
    private static java.util.logging.LogManager logManager =
            java.util.logging.LogManager.getLogManager();

    /* This Logger */
    private static Logger me=null;

    //
    private java.util.logging.Logger logger=null;

    /**
     * Finds a logger(subSystem) by a specified name
     * @param system name of logger to be found
     * @return true if the specified logger exits; false if otherwise
     */
    private static boolean findLogger(String system) {
        boolean found  = false;

        //finding system from registered subSystems
        Enumeration<String> subSystems = logManager.getLoggerNames();
        while(subSystems.hasMoreElements()){
            if(subSystems.nextElement().equalsIgnoreCase(system)){
                found = true;
                break;
            }
        }
        return found;
    }


   /**
    * Constructor which finds the logger for the system specified. If System does
    * not exist then it is created. Information logged are then sent to this
    * system using the FileHandler.
    * @param system
    */
    private Logger(String system){
        logger=java.util.logging.Logger.getLogger(system);
        try {
            String logFile=LOG_DIR+system+".txt";
            FileHandler fh = new FileHandler(logFile,false);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).
                    log(Level.FINER,
                    "Error occurred while writing to file", ex);
        } catch (SecurityException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).
                    log(Level.FINER, "No logging permission", ex);
        }
        logManager.addLogger(logger);
    }

    /**
     * Utility method to find the specified logger. If logger exits then it is
     * returned. If it does not exist the it is created and returned
     * Single Pattern is used so that only one instance of the logger can be
     * used in each class.
     *
     * @param system the logger to find or create
     * @return the logger instance to be used.
     */
   public static java.util.logging.Logger getLogger(String system){
       if(findLogger(system)){
           me.logger = logManager.getLogger(system);
       }else{
           me = new Logger(system);
       }
       return me.logger;
   }
}
