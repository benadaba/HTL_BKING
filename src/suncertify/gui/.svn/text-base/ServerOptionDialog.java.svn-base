/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import javax.swing.*;
import suncertify.server.RegisterDatabase;
import suncertify.util.Logger;

/**
 * Window which allows the user to select database location and run the RMI registry
 * At the very first run of the window, the database path is empty, but on
 * subsequent runs, the previous database path location would have been saved
 * and it is then used.
 *
 * @author Bernard
 */
public class ServerOptionDialog extends JFrame implements ActionListener{

     /**
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>gui</code>.
     */
    private static  java.util.logging.Logger log = Logger.getLogger( "gui" );

    /* Internal reference to the database location */
    private String dbPath ="";

    /* Takes the path of the database location */
    private JTextField dbLocationTextField;

    /* Stops the server */
    private JButton exitButton;

    /* Starts the server */
    private JButton startButton;

    /* Selects the database location */
    private JButton selectButton ;

    /** 
     * Creates new form ServerOptionDialog
     * if there is a previously saved configuration for the database location
     * this value is set as the dbPath.
     */
    public ServerOptionDialog() {
       log.entering("ServerOptionDialog", "Constructor");

        dbPath=SavedConfiguration
                .getSavedConfiguration()
                    .getParameter(SavedConfiguration.DATABASE_LOCATION);
        initComponents();

        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
         int x=(int)((d.getWidth()-getWidth())/2);
        int y=(int)((d.getHeight()-getHeight())/2);
        setLocation(x,y);

       log.exiting("ServerOptionDialog", "Constructor");
    }


    /* Initialises and sets the properties of the components used in this window */
      private void initComponents() {
       log.entering("ServerOptionDialog", "initComponents");

        setTitle("Server Database Starter");
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel databaseLocationLabel = new JLabel();
        databaseLocationLabel.setText("Database Location");

        //Button which selects location of database with its attributes */
        selectButton = new JButton();
        selectButton.setText("Select...");
        selectButton.setToolTipText("Select database location");
        selectButton.setMnemonic(KeyEvent.VK_E);
        selectButton.addActionListener(this);

        dbLocationTextField = new JTextField();
        dbLocationTextField.setToolTipText("Enter database location");
        dbLocationTextField.setText(SavedConfiguration
                .getSavedConfiguration()
                    .getParameter(SavedConfiguration.DATABASE_LOCATION));
        dbLocationTextField.addActionListener(this);

        /* Button which starts the RMI registry, with its attributes */
        startButton = new JButton();
        startButton.setText("Start");
        startButton.setToolTipText("Start server");
        startButton.setMnemonic(KeyEvent.VK_S);
        startButton.addActionListener(this);

        /* Button which stops the RMI registry, with its attributes */
        exitButton = new JButton();
        exitButton.setText("Exit Connection ");
        exitButton.setMnemonic(KeyEvent.VK_X);
        exitButton.setEnabled(false);
        exitButton.addActionListener(this);

        /* Use group layout to contain items*/
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(databaseLocationLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(startButton)
                    .addComponent(dbLocationTextField, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(selectButton, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitButton))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dbLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectButton)
                    .addComponent(databaseLocationLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(startButton))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pack();

      log.exiting("ServerOptionDialog", "initComponents");
    }


     /**
      * Handles all the action events:
      * Chooses the database location by looking in the current working directory
      * when the Select button is pressed,
      * starts the RMI registry when the Start button is pressed, and
      * stops the RMI registry when the Exit Connection button is pressed
      * @param evt event generated when any of the aforementioned buttons is
      *         is pressed
      */
     public void actionPerformed(ActionEvent evt) {
         log.entering("ServerOptionDialog", "actionPerformed");
         log.fine("Event details :"+evt.paramString());
         Object source=evt.getSource();

        if (source == selectButton) {
            JFileChooser fc=new JFileChooser(".");
            int returnedValue = fc.showDialog(new ServerOptionDialog(), "Select");
            if(returnedValue==JFileChooser.APPROVE_OPTION){
                dbPath= fc.getSelectedFile().getAbsolutePath();
                dbLocationTextField.setText(dbPath);

                log.fine("Updating the saved configurations with the chosen file");
                SavedConfiguration.
                        getSavedConfiguration().
                                setParameter(SavedConfiguration.DATABASE_LOCATION, dbPath);

            }
         } else if (source==startButton || source == dbLocationTextField ){
            dbPath=dbLocationTextField.getText();

            log.fine("Updating the saved configurations with the chosen file");
            SavedConfiguration.
               getSavedConfiguration().
                     setParameter(SavedConfiguration.DATABASE_LOCATION, dbPath);

            if(!dbPath.endsWith(".db")) {
              log.info("The selected file does not have the right \".db\" format");
              dbPath = "";
            }

       if (!dbPath.isEmpty()) {
                try {
                    RegisterDatabase.register(dbPath);
                    startButton.setEnabled(false);
                    setTitle("Server Running...");
                    exitButton.setEnabled(true);
                    exitButton.setToolTipText("Stop and exit server");
                } catch (Exception ex) {
                     ApplicationRunner.manageError(this, ex.getMessage());
                     log.log(Level.FINER, ex.getMessage(), ex);
                     System.exit(1);
                }
           
        } else {
              String msg = "Unsupported file format\n"
                +"Select a valid Database file before starting server";
              ApplicationRunner.manageError(this, msg);
        }
    } else if (source == exitButton)  {
        {
            try {
                RegisterDatabase.unregister();
            } catch (Exception ex) {
               ApplicationRunner.manageError(this, ex.getMessage());
                     log.log(Level.FINER, ex.getMessage(), ex);
            }finally{
                System.exit(0);
            }
        }
    }
        log.exiting("ServerOptionDialog", "actionPerformed");
  }
}