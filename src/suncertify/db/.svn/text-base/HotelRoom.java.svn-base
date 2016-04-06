
package suncertify.db;
import java.io.Serializable;

/**
 * An instance of this class represents hotel room data.
 *
 * @author Bernard
 */
public class HotelRoom implements Serializable {

 
   /**
    * ID used for interoperability
    */
    public static final long serialVersionUID = 1L;

    /**
     * the field length of the customer holding this hotel room record
     */
    public static final int OWNERID_LENGTH = 8;

    /**
     * Field length of hotel room name;
     */
    public static final int NAME_LENGTH = 64;

    /**
     * Field length of hotel room location;
     */
    public static final int LOCATION_LENGTH = 64;

    /**
     *Field length of hotel room size;
     */
    public static final int SIZE_LENGTH = 4;

    /**
     *Field length of whether is not smoking is allowed in hotel room;
     */
    public static final int SMOKING_LENGTH = 1;

    /**
     *Field length of room rate;
     */
    public static final int RATE_LENGTH = 8;

    /**
     *Field length of date on which hotel room will be available;
     */
    public static final int DATE_LENGTH = 10;

    /**
     *Total number of fields in each record;
     */
    public static final int TOTAL_FIELD=7;

    /**
     * Total field length of all hotel room fields;
     */
    public static final int RECORD_LENGTH = NAME_LENGTH
                                     + LOCATION_LENGTH
                                     + SIZE_LENGTH
                                     + SMOKING_LENGTH
                                     + RATE_LENGTH
                                     + DATE_LENGTH
                                     + OWNERID_LENGTH;

    //Indexes of the fields of this hotel room record
    /**
     * Name field index
     */
    public static final int NAME_FIELD=0;

    /**
     * Location field index
     */
    public static final int LOCATION_FIELD=1;

    /**
     * Size field index
     */
    public static final int SIZE_FIELD=2;

    /**
     * Smoking field index
     */
    public static final int SMOKING_FIELD=3;

    /**
     * Rate field index
     */
    public static final int RATE_FIELD =4;

    /**
     * Date field index
     */
    public static final int DATE_FIELD =5;

    /**
     * Owner field index
     */
    public static final int  OWNERID_FIELD =6;
   /* Customer holding this hotel room */
    private String owner ="";

   /* Hotel name */
    private String name;

    /* City where this hotel is located */
    private String location;

    /* Maximum occupancy of this hotel room, not including infants */
    private String size ;

    /* Indicates whether this hotel room is smoking or non-smoking */
    private String smoking ;

   /* Price per night of this hotel room */
    private String rate ;

   /* Date that this hotel room is/was available */
    private String date;

    /**
     * Creates an instance of this object with default values.
     * Just for use in the Test Class
     */
    public HotelRoom() {
    //    log.info("HotelRoom empty constructor called");
    }


    /**
     * Creates an instance of this object with a specified list of
     * initial values having been mainly retrieved from the database file.
     * @param name Hotel name
     * @param location City
     * @param size Maximum occupancy of this hotel room, not including infants
     * @param smoking whether room is smoking or non-smoking
     * @param rate Price per night
     * @param date Date available
     */
    public HotelRoom( String name, String location, String size,String smoking,
                  String rate, String date){
  

    this.name = name;
    this.location = location;
    this.smoking = smoking;
    this.rate = rate;
    this.date = date;
    this.size = size;


}

   

 /**
 * Sets the owner id of the customer holding this record
 * @param owner id
 */
public void setOwner(String owner){

   this.owner=owner;
}

/**
 * Gets the owner id of the customer holding this hotel room  record
 * @return (customer) id value.
 */
public String getOwner(){

   return owner;

}

/**
 * Sets the name of this hotel room
 * @param name hotel name
 */
public void setName(String name){

    this.name=name;

}

/**
 * Gets the name of the hotel this record relates to
 * @return hotel name
 */
public String getName(){

    return name;
}

/**
 * Sets the maximum occupancy of this hotel room
 * @param size the number of people permitted in this room not including
 * infants
 */
public void setSize(String size){

    this.size=size;

}

/**
 * Returns the maximum number of people permitted in this room
 * not including infants.
 * @return maximum number of people permitted in this room
 * not including infants
 */
public String getSize(){

    return size;
}

/**
 * Sets whether or not smoking is permitted in this hotel room
 * @param canSmoke boolean indicating whether or not smoking is permitted
 * : true indicating permitted and false indicating not permitted.
 */
public void setSmoking(boolean  canSmoke){

    smoking=canSmoke?"Y":"N";

}

/**
 * Get whether this hotel room is smoking (smoking is permitted) or non-smoking
 * (smoking is not permitted)
 * @return "Y" indicating a smoking room, or "N" indicating a non-smoking room
 */
public String getSmoking(){

    return smoking;
}

/**
 * Sets price per night of this hotel room
 * @param rate price per night
 */
public void setRate(String rate){
    this.rate=rate;
}

/**
 * Get Price per night of this hotel room
 * @return figure(String) indicating Price per night
 */
public String getRate(){
    return rate;
}

/**
 * Sets the single night to which this record relates.
 * @param date Date available
 */
public void setDate(String date){
   this.date=date;
}

/**
 * Get date that this hotel room is/was available
 * @return String containing the date on which this hotel room is/was available
 */
public String getDate(){
    return date;
}

/**
 * Sets the city where this hotel is located
 * @param location City
 */
public void setLocation(String location){
   this.location=location;
}

/**
 * Get the city where this hotel is located
 * @return String containing the name of city where this hotel is located
 */
public String getLocation() {
    return location;
    }

/**
 * Gets the field values of this hotel room object separated by comma
 * @return String array which contains all the values of the fields of this
 *     hotel room record.
 */
public String[] getFields(){
    return toString().split(",");
}

/**
 * Gets the fields title as string seperated by commas
 * @return String array which contains the title of  record fields
 */
public static String[] getTitle(){
    return "Name,Location,Size,Smoking,Rate,Date,OwnerID".trim().split(",");
}

/**
 * Indicates whether some other hotel room object is equal to this hotel
 * room object.
 * This is determine by comparing all the variables with the exception of
 * the owner variable.
 * @param someHotelRoom the other hotel room object to be compared to this
 *   hotel room object
 * @return true if someHotelRoom is equal to this hotel room , or false if otherwise
 */
    @Override
public boolean equals(Object someHotelRoom){

    if(!(someHotelRoom instanceof HotelRoom)) {
            return false;
        }
     HotelRoom anotherHotelRoom = (HotelRoom) someHotelRoom;
     if(!anotherHotelRoom.name.equalsIgnoreCase( name)) {
            return false;
        }
     if(!anotherHotelRoom.location.equalsIgnoreCase(location)) {
            return false;
        }
     if(!anotherHotelRoom.size.equalsIgnoreCase(size)) {
            return false;
        }
     if(!anotherHotelRoom.smoking.equalsIgnoreCase(smoking)) {
            return false;
        }    
     if(!anotherHotelRoom.rate.equalsIgnoreCase(rate)) {
            return false;
        }
     if(!anotherHotelRoom.date.equalsIgnoreCase(date)) {
            return false;
        }
     return true;
}

 /**
     * Returns a hash code value for this hotel room object
     * @return integer representing the hash code value for this object.
     */
@Override
public int hashCode() {
    int hash = 7;
    hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
    hash = 29 * hash + (this.location != null ? this.location.hashCode() : 0);
    hash = 29 * hash + (this.size != null ? this.size.hashCode() : 0);
    hash = 29 * hash + (this.smoking != null ? this.smoking.hashCode() : 0);
    hash = 29 * hash + (this.rate != null ? this.rate.hashCode() : 0);
    hash = 29 * hash + (this.date != null ? this.date.hashCode() : 0);
    return hash;
}


/**
 * Returns a String representation of this HotelRoom class.
 * @return A String representation of this HotelRoom class.
 */
@Override
public String toString(){

    return
            name  + "," +
            location + "," +
            size  + "," +
            smoking + "," +
            rate + "," +
            date + "," +
            owner + ",\n";
}

}