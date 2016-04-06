/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.gui;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import suncertify.util.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import suncertify.db.DBClient;
import suncertify.db.LocalClient;



/**
 * Window which allows the user to run the application in a
 * non - networked mode (alone).
 * At the very first run of the window, the database path is empty, but on
 * subsequent runs, the previous database path location would have been saved
 * (configured) and it is then used.
 *
 * @author Bernard
 */
public class AloneOptionDialog extends JFrame implements ActionListener{

    /* Reference to the database location */
    private String dbPath="";

    /* Text field for the  path to the database in non-networked mode */
    private JTextField fileTextField;

    /* Describes the text field for the  path to the database in non-networked mode */
    private JLabel localDatabaseLabel;

    /* Selects the location of the database in non-networked mode */
    private JButton selectButton;

    /* Connects the database in non-networked mode */
    private JButton runButton;

    /*
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>gui</code>.
     */
    private static java.util.logging.Logger log = Logger.getLogger( "gui" );

    /**
     * Creates new form AloneOptionDialog
     */
    public AloneOptionDialog() {
        log.entering("AloneOptionDialog", "Constructor");

        /* centers the window */
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        int x=(int)((d.getWidth()-getWidth())/2);
        int y=(int)((d.getHeight()-getHeight())/2);
        setLocation(x,y);

        initComponents();

        log.exiting("AloneOptionDialog", "Constructor");
    }

   /* Initialises and sets the properties of the components used in the window */
    private void initComponents() {
        log.entering("AloneOptionDialog", "initComponents");

        setTitle("Alone Database Mode");
        setResizable(false);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        localDatabaseLabel = new JLabel();
        localDatabaseLabel.setText("Database Location");

        fileTextField = new JTextField();
        fileTextField.setText(SavedConfiguration
                .getSavedConfiguration()
                    .getParameter(SavedConfiguration.DATABASE_LOCATION));
        fileTextField.setToolTipText("Enter Database Location");



        fileTextField.addActionListener(this);

        selectButton = new JButton();
        selectButton.setText("Select...");
        selectButton.setToolTipText("Select local database location");
        selectButton.setMnemonic(KeyEvent.VK_S);
        selectButton.addActionListener(this);

        runButton = new JButton();
        runButton.setText("Run");
        runButton.setToolTipText("Connect to local database");
        runButton.setMnemonic(KeyEvent.VK_R);
        runButton.addActionListener(this);
        
       //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(localDatabaseLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.
                        Alignment.LEADING)
                    .addComponent(runButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileTextField, GroupLayout.PREFERRED_SIZE,
                        117, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectButton)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.
                        Alignment.BASELINE)
                    .addComponent(localDatabaseLabel)
                    .addComponent(fileTextField, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runButton)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();

       log.exiting("AloneOptionDialog", "initComponents");
    }

   /**
    * Handles all the action events in this class.
    * Processes database file path selection when the selectButton is pressed.
    * the database location configuration is obtained in this method
    * and assigned it to the dbPath variable.
    * And the method spawns a thread to run the Application when the
    * run button is pressed
    *
    * @param evt the event generated
    */
   public void actionPerformed(ActionEvent evt) {
       log.entering("AloneOptionDialog", "actionPerformed", evt);
       log.fine("Event details :"+evt.paramString());
       Object source=evt.getSource();
       if (source==selectButton) {
            JFileChooser fc=new JFileChooser(".");
            int returnedValue=fc.showDialog(this, "Select");
            if (returnedValue==JFileChooser.APPROVE_OPTION) {
               dbPath= fc.getSelectedFile().getAbsolutePath();
               fileTextField.setText(dbPath);
               SavedConfiguration.
                  getSavedConfiguration().
                     setParameter(SavedConfiguration.DATABASE_LOCATION, dbPath);
            }
        }else if(source==runButton ||source==fileTextField){
           dbPath=fileTextField.getText();
           //save recent database location to the configuration
           SavedConfiguration.
                getSavedConfiguration().
                     setParameter(SavedConfiguration.DATABASE_LOCATION, dbPath);

            //verify if file has a ".db" extension
            if(!dbPath.endsWith(".db")) {
                log.fine("The selected file does not have " +
                        "the right format \".db");
                dbPath = "";
            }
            if (!dbPath.isEmpty()) {
                try {
                    final DBClient db = new LocalClient(dbPath);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                     public void run() {
                        new ApplicationWindow(db).setVisible(true);
                      }
                    });
                } catch (FileNotFoundException ex) {
                    String msg =  "File cannot be located\n"
                                 + "Please select a valid file location";
                    ApplicationRunner.manageError(this, msg);
                    log.finer(ex.getMessage());
                } 
                dispose();
            }else{
                  String msg =  "Unsupported file format\n"
                                +"Select a valid Database " +
                                "file before running program";
                  ApplicationRunner.manageError(this, msg);
                  log.fine("Invalid database file format of file selected");
            }
        }
        log.exiting("AloneOptionDialog", "actionPerformed", evt);
    }
}
