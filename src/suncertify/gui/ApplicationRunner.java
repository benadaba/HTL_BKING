/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.logging.Level;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import suncertify.server.RemoteConnector;
import suncertify.util.Logger;


/**
 * The window which starts the initial run of the whole application.
 * Connects to the right networking mode either networked
 * or non - networked based on the user's specification.
 * @author Bernard
 */
public class ApplicationRunner {

 /**
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>gui</code>.
     */
  private static java.util.logging.Logger log = Logger.getLogger( "gui" );

  /** Part message displayed when there is a network connection problem */
  private static String errorMsg = "Unable to connect to the database\n" +
                                           "Please check";
    /**
     * Main method that starts the application.
     * Takes the command line arguments specified to establish the necessary connection
     * to the database.
     * @param args mode to run in
     */
    public static void main(String args[]) {
         log.entering("ApplicationRunner", "main", args);

        if ( args.length==0 ) {
             log.info("networkwork client mode selected");
             final  String host = "localhost";
             java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
                 boolean canExit=true;
                 try {
                        new ApplicationWindow(RemoteConnector.getRemote(host)).
                                setVisible(true);
                        canExit=false;

                 } catch ( NotBoundException ex ) {
                     String msg = " Server connection";
                     manageError(null, errorMsg+msg );
                    log.log(Level.FINER, "Unbinding failed", ex);

                 } catch ( MalformedURLException ex ) {
                      String msg = " the host specification";
                      manageError(null, errorMsg+msg );
                      log.log(Level.FINER, "There is a problem with " +
                                        "the localhost", ex);

                 } catch ( RemoteException ex ) {

                      String msg = " the Server connection";
                      manageError(null, errorMsg+msg);
                      log.log(Level.FINER, "Remote Connection failure", ex);
                      

                 }catch ( FileNotFoundException ex ) {

                     String msg = "File cannot be located\n"
                        +"Please select a valid file locationase";
                     manageError(null, msg);
                     log.log(Level.FINER, "File was not found", ex);
                     
                 }
                 if(canExit) {
                        System.exit(1);
                    }
                }
             });

            } else {
                if("Alone".equalsIgnoreCase(args[0])){
                    java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new AloneOptionDialog().setVisible(true);
                        log.info("standalone mode selected");
                    }
                });
                }else if("Server".equalsIgnoreCase(args[0])){
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new ServerOptionDialog().setVisible(true);
                              log.info("server flag selected");
                        }
                    });
                } else {

                     String msg = "Please specify a valid connection type:\n"
                           + " Either \"alone\" or \"server\"";
                    manageError(null, msg);

                    log.info("Invalid connection type specified");
                }
             }
                log.exiting("ApplicationRunner", "main");
     }

     /**
     * Prompts the user with a message in an attention window
     * with the outcome of an operation.
     *
      * @param msg  message displayed in the attention  window.
      * @param frame parent frame in which this dialogue is displayed
     */
    public static void manageError(JFrame frame, String msg ) {
        JOptionPane attention = new JOptionPane(msg,
                                            JOptionPane.ERROR_MESSAGE,
                                            JOptionPane.DEFAULT_OPTION);
        JDialog msgDialog = null;
        
        if(frame != null){
          msgDialog =  attention .createDialog(frame, "Attention");
        }else{
        msgDialog = attention .createDialog(null, "Attention");
        }
        // Center on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - msgDialog.getWidth()) / 2);
        int y = (int) ((d.getHeight() - msgDialog.getHeight()) / 2);
        msgDialog.setLocation(x, y);

        msgDialog.setVisible(true);
    }


}
