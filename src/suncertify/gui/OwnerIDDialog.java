/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.logging.Level;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;
import suncertify.db.HotelRoom;
import suncertify.util.Logger;

/**
 * Accepts the owner ID entered by the user and books the selected room.
 *
 * @author Bernard
 */
public class OwnerIDDialog extends JDialog implements ActionListener{



     /**
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>gui</code>.
     */
    private static  java.util.logging.Logger log = Logger.getLogger( "gui" );

  /*
   * Handle to the main window of the application
   */
    private ApplicationWindow parent;

   /*
    *FormattedTextField to accept the owner ID inputed by user
    * Used a FormattedTextField because it allows you to set the maximum number
    * of digits to receive in the texfield and helps check any invalid data like
    * alphabets in this case.
    */
    private JFormattedTextField ownerFormattedTextField;


    /* The row selected from the table to be booked*/
    private int row ;


    /**
    * Creates new form OwnerIDDialog
    * @param parent the <code>Frame</code> from which the dialog is displayed
    * @param modal  specifies whether dialog blocks user input to other top-level
    *     windows when shown.
    * @param selectedRow row selected from the table corresponding to the room to
    *                  be booked
    */
    public OwnerIDDialog (ApplicationWindow parent, boolean modal,
            int selectedRow) {

        super(parent, modal);

        log.entering("OwnerIDDialog", "Constructor",
                new Object[]{modal,selectedRow});
        row = selectedRow;
        initComponents();
        this.parent=parent;
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        int x=(int)((d.getWidth()-getWidth())/2);
        int y=(int)((d.getHeight()-getHeight())/2);
        setLocation(x,y);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
                    public void windowClosing(WindowEvent e) {
                        dispose();
                    }
                });
       log.exiting("OwnerIDDialog", "Constructor");
    }

    /**
     * Initialises and sets the properties of the components this dialog.
     */
    private void initComponents() {
        log.entering("OwnerIDDialog", "initComponents");

        setTitle("Owner ID Input  Dialog");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
       
        JLabel ownerIDLabel = new JLabel("Enter Owner ID");

        //Books the selected room when a valid owner ID is entered in text field
        JButton bookButton = new JButton("Book");
        bookButton.setToolTipText("Book room");
        bookButton.setMnemonic(KeyEvent.VK_B);
        bookButton.addActionListener(this);

        //Accepts only valid 8 digit ID and books room when enter key is pressed
        ownerFormattedTextField = new JFormattedTextField(
            createFormatter());
        ownerFormattedTextField.setColumns(8);
        ownerFormattedTextField.setToolTipText("Enter an eight digit ID");
        ownerFormattedTextField.addActionListener(this);

        setLocationByPlatform(true);
        setModal(true);
        setResizable(false);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(ownerIDLabel)
                .addGap(18, 18, 18)
                .addComponent(ownerFormattedTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(bookButton)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(bookButton)
                    .addComponent(ownerFormattedTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(ownerIDLabel))
                .addContainerGap())
        );      
        pack();
        log.exiting("OwnerIDDialog", "initComponents");
    }

   /**
    * Specifies the valid characters that can be contained in the
    * JFormattedTextField used to accept the owner ID. In this case,
    * an 8 digit number
    *
    * @return MaskFormatter instance of valid characters to accept.
    */
     public  MaskFormatter createFormatter(){
            log.entering("OwnerIDDialog", "createFormatter");

            MaskFormatter formatter=null;
            try {

                formatter = new MaskFormatter("########");  //takes an 8 digit number
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(
                        this, "Enter 8 digits only "+ex.getMessage());
                log.log(Level.FINER, null, ex);
            }
            log.exiting("OwnerIDDialog", "createFormatter",formatter);
            return formatter;
    }

     /**
      * Processes the booking event when the book menu item is selected.
      * Books a selected room from the table.
      * Whenever a booking activity is processed a new  HotelRoomTableModel
      * instance is created and the table's model is set to the new
      * HotelRoomTableModel instance created. This makes the old  
      * HotelRoomTableModel eligible for garbage collection and would be
      * garbage collected appropriately.
      * This new creation does not however happen when the client is disconnected
      * from the server when running in networked mode
      * @param evt event generated when the book menu item  is selected
      */
    public void actionPerformed(ActionEvent evt) {
            log.exiting("OwnerIDDialog", "actionPerformed", evt);
            String owner ="";
            owner=ownerFormattedTextField.getText();
            owner=owner.trim();
            Object[][] data = null;
            if(owner.isEmpty()){
               JOptionPane.showMessageDialog(this, "Enter an 8 digits Number\n");
               return;
            }

           int length=HotelRoom.TOTAL_FIELD;
           String recordValues[] = new String[length];

           for (int i= 0; i<length; i++)    {
              recordValues[i] = (String)parent.hotelRoomTable.getValueAt(row,i);
           }

           HotelRoom room = new HotelRoom(recordValues[HotelRoom.NAME_FIELD],
                                recordValues[HotelRoom.LOCATION_FIELD],
                                recordValues[HotelRoom.SIZE_FIELD],
                                recordValues[HotelRoom.SMOKING_FIELD],
                                recordValues[HotelRoom.RATE_FIELD],
                                recordValues[HotelRoom.DATE_FIELD]);

           try {
                  boolean result=parent.roomClient.bookHotelRoom(owner,room);
                  String msg ="";
                    data =   parent.roomClient.getAllRooms();
                    if(data!=null){
                      parent.hotelRoomTable.setModel(new HotelRoomTableModel(
                              data,  HotelRoom.getTitle()));
                      parent.hotelRoomTable.repaint();
                    }     
                  if(result){
                    msg = "Booking is Successful\n" +
                          "Congratulations! and \n Thank you for doing " +
                          "business with URlyBird.";
                  }else{
                      msg = "Booking Not Successful\n" +
                                    "Sorry, Room not available!";
                  }                    
                  JOptionPane.showMessageDialog(this, msg );
           } catch (Exception ex){
               System.out.println("exception in server disconnet" +
                       ""+ ex + ex.getMessage());
                 String msg = "Booking Not Successful\n" +
                                    "Sorry, Room not available!";
               if(parent.roomClient!= null){
                 String serverMsg = "Server is disconnected\n";
                 msg= serverMsg + msg;
               }
                    ApplicationRunner.manageError(null, msg);
                    log.info("Room is not available ");
            } finally {
                dispose();
            }
            log.exiting("OwnerIDDialog", "actionPerformed", evt);
     }    
}
