/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.gui;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import suncertify.util.Logger;


/**
 * Responsible for reading the file which contains the Application's
 * userguide.
 * @author Bernard
 */
public class InstructionsDialog extends JDialog{

    /* Users' instructions message*/
    private static final String FILENAME=".\\docs\\userguide.txt";

    /* Holds the user's instructions which is read from the file*/
    private String content;

    /*
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>gui</code>.
     */
  private static java.util.logging.Logger log = Logger.getLogger( "gui" );

   /**
    * The text area and scroll pane  with their properties for displaying the
    * userguide are created in this constructor
    */
   public InstructionsDialog(){

        super();

        log.entering("ApplicationInstruction"," Constructor");
        this.setModal(false);
        this.setTitle("URLyBird HOTEL ROOM BOOKING APPLICATION - USERGUIDE");
        init();
        JTextArea text = new JTextArea(10, 40);
        text.setFont(new Font("SansSerif", Font.BOLD, 14));
        text.setText(content);
        text.setEditable(false);

        Box box = Box.createHorizontalBox(); // create box
        box.add( new JScrollPane( text ) ); // add scrollpane

        add( box ); // add box to frame

        addWindowListener(new WindowAdapter(){ //process closing window events
                       public void windowClosing(WindowEvent e) {
                               dispose();//release window resources
                       }//end method windowClosing
              });//end inner class WindowAdapter
        setSize(600, 700);
        this.setVisible(true);
       log.exiting("ApplicationInstruction"," Constructor");
    }

    /*
     * Uses input inputStream to read bytes from the user's instructions
     * and store them in the  form String of the US-ASCII type.
     */
   private void init() {
        log.entering("ApplicationInstruction"," init");
        String msg = "Error occurred while reading the userguide";
        try {
            File file=new File(FILENAME);
            FileInputStream input = new FileInputStream(file);
            int size=(int)file.length();
            byte[] data=new byte[size];
            input.read(data);
            input.close();
            content=new String(data,"US-ASCII");
        } catch (FileNotFoundException ex) {
           ApplicationRunner.manageError(null, msg);
           log.log(Level.FINER, "The file was not found", ex);
        }catch (IOException ex) {
            ApplicationRunner.manageError(null, msg);
            log.log(Level.FINER, msg, ex);
         }

        log.exiting("ApplicationInstruction"," init");
   }

}
