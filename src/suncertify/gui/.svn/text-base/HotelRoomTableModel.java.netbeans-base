/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suncertify.gui;

import java.util.logging.Level;
import suncertify.util.Logger;
import javax.swing.table.AbstractTableModel;


/**
 * A custom table that the ApplicationWindow uses.
 * @author Bernard
 */
public class HotelRoomTableModel extends AbstractTableModel {

    /**
     * The main Logger instance for this class. All log messages from
     * this class are logged through this member.
     * The Logger namespace is <code>gui</code>.
     */
  private static  java.util.logging.Logger log = Logger.getLogger( "gui" );

   /**
    * Holds the hotel room records in the form of object array
    */
   private Object  data[][]=null;

   /* Holds the title of each of the hotel room records' fields */
   private String headers[];

   /**
    * Initialises the String array which holds the  hotel room records to be
    * displayed in the table in the ApplicationWindow as well as the associated
    * title of the hotel room fields.
    * @param data Object array which contains the hotel room records' fields
    *                   values
    * @param header the title of each of the field values of the rooms.
    */
    public HotelRoomTableModel(Object data[][], String header[]) {
          log.entering("HotelRoomTableModel", "Constructor",
                    new Object[][]{data, headers});
	this.headers=header;
        this.data=data;
           log.exiting("HotelRoomTableModel", "Constructor",
                 new Object[][]{data, headers});
    }

    /**
     * Returns the number of rows in the table.
     *
     * @return An integer indicating the number of rows in the table.
     */
    public int getRowCount() {
           log.entering("HotelRoomTableModel", "getRowCount");
           log.exiting("HotelRoomTableModel", "getRowCount",data.length);
           return data.length;
    }

    /**
     * Returns the column count of the table.
     *
     * @return An integer indicating the number or columns in the table.
     */
    public int getColumnCount() {
          log.entering("HotelRoomTableModel", "getColumnCount");
          log.exiting("HotelRoomTableModel", "getColumnCount",headers.length);
          return headers.length;
    }

    /**
     * Gets a value from a specified index in the table.
     *
     * @param rowIndex An integer representing the row index.
     * @param columnIndex An integer representing the column index.
     * @return The object located at the specified row and column.
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
         log.entering("HotelRoomTableModel", "getValueAt",
                new Object[]{rowIndex, columnIndex});
        if(columnIndex<0 || columnIndex>headers.length){
            log.log(Level.SEVERE, "Invalid column  parameter "+columnIndex);
            return null;
        }
        if(rowIndex<0 || rowIndex>data.length){
            log.log(Level.SEVERE, "Invalid row  parameter "+rowIndex);
            return null;
        }

         log.exiting("HotelRoomTableModel", "getValueAt",
                 data[rowIndex][columnIndex]);
         return data[rowIndex][columnIndex];
    }

    /**
     * Returns the name of a column at a given column index.
     *
     * @param column The specified column index.
     * @return A String containing the column name.
     */
      public String getColumnName(int column) {
        log.entering("HotelRoomTableModel", "gegetColumnName", column);
        if(column<0 || column>headers.length){
            log.log(Level.SEVERE, "Invalid parameter "+column);
            return "";
        }
        log.exiting("HotelRoomTableModel", "getColumnName", headers[column]);
        return headers[column];
      }

    /**
     * Given a row and column index, indicates if a table cell can be edited.
     *
     * @param rowIndex Specified row index.
     * @param columnIndex Specified column index.
     * @return A boolean indicating if a cell is editable.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
         log.entering("HotelRoomTableModel", "isCellEditable",
                new Object[]{rowIndex, columnIndex});
         log.exiting("HotelRoomTableModel", "isCellEditable",false);
         return false;
    }

}
