/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException; 
import suncertify.util.Logger;
import suncertify.db.*;


/**
 * Main window of the URlyBird Hotel Room Booking Application.
 * Displayed when either running in networked or non - networked mode.
 * 
 * @author Bernard
 */
public class ApplicationWindow extends JFrame implements ActionListener{

      /*
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>gui</code>.
     */
  private static  java.util.logging.Logger log = Logger.getLogger( "gui" );

     /*
      * Takes the location of the hotel room
      */
    private JTextField locationTextField;

    /*
     *Takes the name of the hotel room
     */
    private JTextField nameTextField;

    /*
     * searches for hotel room(s)
     */
    private JButton searchRoomButton;

    /*
     * Pops out the Application's userguide
     */
    JMenuItem userguideMenuItem;

    /*
     * Books a selected hotel room from the JTable
     */
    JMenuItem bookMenuItem;

    /*
     * Closes the application when selected
     */ 
    JMenuItem exitMenuItem;


    /*Handle to the DBClient which has methods which the user will call*/
    DBClient roomClient;

    /*
     * Main table which displays the room
     */
     JTable hotelRoomTable;

  
    /**
     * Constructor of the main window which is displayed when the application
     * runs. Has a handle to the DBClient which holds the instance of the
     * current running mode of the Application. Uses the appropriate instance of
     * the DBClient passed into this constructor by the ApplicationRunner to obtain
     * the rooms (records) needed to load the application.
     * @param client instance of the object running; either local or remote 
     * object.
     */
    public ApplicationWindow( DBClient client ) {
        log.entering( "ApplicationWindow", "Constructor", client );

        roomClient  =  client;
        try {
            initComponents();
            hotelRoomTable.setModel(
                    new HotelRoomTableModel(roomClient.getAllRooms(),
                                            HotelRoom.getTitle() ) );
        } catch ( IOException ex )  {
           log.finer( "Unable to obtain room record"+ ex.getMessage() );

        }

        addWindowListener(new AppWindowAdapter());

        //positions window in the centre of the screen.
        Dimension d = Toolkit.getDefaultToolkit(  ).getScreenSize(  );
        int x = ( int )( ( d.getWidth()-getWidth() )/2 );
        int y = ( int )( ( d.getHeight()-getHeight() )/2 );
        setLocation( x,y );


        log.exiting( "ApplicationWindow", "Constructor" );
    }


    /*
     * Initialises and sets the properties the main components used by the
     * application's window.
     */
   private void initComponents() {
      log.entering( "ApplicationWindow"," initComponents" );

       //Set Application window title
       setTitle( "URLyBird HOTEL ROOM BOOKING APPLICATION" );
       setDefaultCloseOperation( DO_NOTHING_ON_CLOSE  );
       //Set Up Menu bar and menu items.

       exitMenuItem  =  new JMenuItem();
       exitMenuItem.setText( "Exit" );
       exitMenuItem.setToolTipText( "Exit Application" );
       exitMenuItem.setMnemonic( KeyEvent.VK_X );
       exitMenuItem.addActionListener( this);

        //Main file menu of the  application
       JMenu fileMenu  =  new JMenu();
       fileMenu.setText( "File" );
       fileMenu.setToolTipText( "File" );
       fileMenu.setMnemonic( KeyEvent.VK_F );
       fileMenu.add( exitMenuItem );

       //Displays the Application's usage instructions
       userguideMenuItem  =  new JMenuItem(  );
       userguideMenuItem.setText( "Instructions" );
       userguideMenuItem.setToolTipText( "Application Instruction" );
       userguideMenuItem.setMnemonic( KeyEvent.VK_I );
       userguideMenuItem.addActionListener( this );

       bookMenuItem  =  new JMenuItem();
       bookMenuItem .setText( "Book Hotel Room" );
       bookMenuItem .setToolTipText( "Book Hotel Room selected from " +
               "the table" );
       bookMenuItem .setMnemonic( KeyEvent.VK_O );
       bookMenuItem .addActionListener(this);

       //Help menu which provides help information and its attributes
       JMenu helpMenu  =  new JMenu();
       helpMenu.setText( "Help" );
       helpMenu.setToolTipText( "Help" );
       helpMenu.setMnemonic( KeyEvent.VK_HELP );
       helpMenu.add( userguideMenuItem  );

       JMenu bookMenu  =  new JMenu();
       bookMenu.setText( "Book" );
       bookMenu.setToolTipText( "Book Hotel Room selected from the table" );
       bookMenu.setMnemonic( KeyEvent.VK_B );
       bookMenu.add(bookMenuItem);

       //Main Menu bar of the application
       JMenuBar mainMenuBar  =  new JMenuBar(  );
       mainMenuBar.add( fileMenu );
       mainMenuBar.add( bookMenu );
       mainMenuBar.add( helpMenu );
       setJMenuBar( mainMenuBar );

       // Set table properties
       hotelRoomTable  =  new JTable();
       hotelRoomTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
       hotelRoomTable.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
       hotelRoomTable.setToolTipText( "Select a hotel room and click" +
               " \"BookRoom \""
               + " button to book the selected room " );

       //set up scrollpanes
       JScrollPane outerScrollPane  =  new JScrollPane();
       JScrollPane innerScrollPane  =  new JScrollPane();
       outerScrollPane.setViewportView( innerScrollPane );
       innerScrollPane.setViewportView( hotelRoomTable );
       outerScrollPane.setSize( 600, 100 );

       //add outerScrollPane to the main window.
       this.getContentPane().add( outerScrollPane,BorderLayout.CENTER );

       //Indicates the name of hotel room to search for
       JLabel nameLabel  =  new JLabel();
       nameLabel.setText( "Enter  Hotel Name " );

       //Indicates the location of the hotel room to search for
       JLabel locationLabel  =  new JLabel();
       locationLabel.setText( "Enter Hotel Location" );

       //search texfields and their properties
       nameTextField  =  new JTextField( 12 );
       nameTextField.setToolTipText( "Enter the name of the Hotel Room you" +
               " are searching for" );
       nameTextField.addActionListener(this);

       locationTextField  =  new JTextField( 12 );
       locationTextField .setToolTipText( "Enter the location of the Hotel " +
               "Room you are searching for" );
       locationTextField.addActionListener(this);

       //search room button and its  function and attributes
       searchRoomButton  =  new JButton();
       searchRoomButton.setText( "SearchRoom" );
       searchRoomButton.setToolTipText( "Search Hotel Rooms" );
       searchRoomButton.setMnemonic( KeyEvent.VK_S );
       searchRoomButton.addActionListener( this );
       searchRoomButton.setPreferredSize(new Dimension(12, WIDTH));

      
      //Sets up panel for finding hotel rooms and its attributes
       JPanel searchPanel  =  new JPanel( new GridLayout(3,2 ));

       searchPanel.setToolTipText( "Search for a hotel room by entering " +
               "the name "
                + " and / or location in the respective textfields" );
       searchPanel.setBorder( BorderFactory.createTitledBorder( "Find Rooms" ));
       searchPanel.add( nameLabel );
       searchPanel.add( nameTextField );
       searchPanel.add( locationLabel );
       searchPanel.add( locationTextField );
       searchPanel.add( new Label() );
       searchPanel.add( searchRoomButton );

       this.getContentPane().add( searchPanel, BorderLayout.NORTH );
       this.setVisible( true );
       pack();

       log.exiting( "ApplicationWindow"," initComponents" );
     }
           /**
            * Processes all action events in the main window.
            * Events for booking and searching of hotel rooms
            * @param evt the event type
            */
         public void actionPerformed(ActionEvent evt) {
              log.entering("ApplicationWindow", "actionPerformed",evt);
              log.fine("Event details :"+evt.paramString());
              Object source=evt.getSource();
              if (source == bookMenuItem ) {
                  final int rowIndex  =  hotelRoomTable.getSelectedRow();
                  if( rowIndex == -1 )  {
                      JOptionPane.showMessageDialog( this,
                              "Please select a row from the table to book",
                              "Select a room",
                              JOptionPane.INFORMATION_MESSAGE );
                  }else{
                      java.awt.EventQueue.invokeLater( new Runnable() {
                          public void run()  {
                            new OwnerIDDialog(ApplicationWindow.this,
                                    true,rowIndex)
                                    .setVisible( true );
                            }
                        });
                  }
             } else if (source== searchRoomButton ||
                      source==nameTextField || source==locationTextField){
                 String[] criteria  =  new String[HotelRoom.TOTAL_FIELD];
                 if ( !nameTextField.getText().isEmpty() )  {
                    criteria[HotelRoom.NAME_FIELD]=  
                            nameTextField.getText().trim();
                 }
                 if ( !locationTextField.getText().isEmpty() )  {
                    criteria[HotelRoom.LOCATION_FIELD]
                        = locationTextField.getText().trim();
                 }
                 log.fine("Search Name "+criteria[HotelRoom.NAME_FIELD]);
                 log.fine("Search Location "+criteria[HotelRoom.LOCATION_FIELD]);
                 try {
                    String[][] data = 
                            roomClient.getHotelRoomsByCriteria( criteria);
    
                    //creates new HotelRoomTableModel to get data from search
                    //result and
                    //sets the internal table's model to new HotelRoomTableModel.
                    hotelRoomTable.setModel( new HotelRoomTableModel(data,
                            HotelRoom.getTitle() ) );
                    hotelRoomTable.repaint();
                 } catch ( IOException ex )  {               
                    String msg = "Server Search Unsuccessful\n";
                    ApplicationRunner.manageError(this, msg);
                    log.finer(ex.getMessage());
                 }
            }else if (source== userguideMenuItem){
                new  InstructionsDialog();
            }else if(source==exitMenuItem){
                actionExit();
            }
            log.exiting("ApplicationWindow", "actionPerformed");
         }


        //Handles  the application's exit events
      private class AppWindowAdapter extends WindowAdapter {
           //Closes the application when method is invoked.

        @Override
            public void windowClosing(WindowEvent e) {
                log.entering(" AppWindowAdapter", "actionPerformed",
                        e.getSource());
                actionExit();
                log.exiting(" AppWindowAdapter", "actionPerformed");

             }
     }

      /*
       * Invoked when the user wants to exit the application through
       * the exit menu item or the default closing icon.
       */
    private void actionExit(){
        log.entering("ApplicationWindow", "close");

        String msg = "Are you sure you want to exit HotelRoom Booking Application";
        int option   =  JOptionPane.showConfirmDialog( this,
            msg,"Exit Application", JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE );

            if( option  == JOptionPane.YES_OPTION ) {
                log.fine("Application close ");
                  dispose();
                  System.exit(0);
              }
          log.exiting("ApplicationWindow", "close");
    }
}
