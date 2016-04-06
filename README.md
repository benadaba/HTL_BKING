HOTEL ROOM BOOKING SYSTEM

USERGUIDE


TABLE OF CONTENTS
	1.	Introduction
	2.	Execution Instructions
	3.	Running Application In Non_Networked Mode
	4.	Running Application In Networked Mode
	5.	How To Search For Hotel Room(s)
	6.	How To Book A Hotel Room
	7.	Exiting The Application


1. INTRODUCTION
URLyBird is a broker of discount hotel rooms. We take bookings only within 48 hours of start of room occupancy.
This Hotel Room Booking System is used to SEARCH and/or BOOK for hotel rooms we sell.




2. EXECUTION INSTRUCTIONS
There are two ways to run the HotelRoom Booking Application: in non_networked mode and in networked mode. In either mode, please change the current working directory to the directory where you have the runme.jar before executing commands. The application should be ran from the command line with the appropriate command as stated in userguide. The virtual machine (PC) should also have the Java Runtime Environment installed on it. After successfully starting the client, you should see a GUI with a search panel at the top and a table from centre to bottom of the displaying window.



3. RUNNING APPLICATION IN NON_NETWORKED MODE

1) On running the application in a non_networked mode, at the command line, specify the command line argument as alone (case ignored) with the following command:

java <hyphen_sign >jar <path_and_filename> [<mode>]

    Where
       path_and_filename = runme.jar
       mode              = alone

Hence command = java <hyphen_sign>jar runme.jar alone


2) Press the return/enter key to run.

3) From the resulting window, select the location of the 
   database file, using the Select button.

If this is NOT the first time the server is being ran, then the last saved location of the database file location should appear in the database location textfield.

4) When the database file location is set, click on the Run button to run the application’s main gui

5) You may exit running the application’s gui by closing the window with the close button at the top right corner of the resulting window.




3. RUNNING APPLICATION IN NETWORKED MODE

1) The server should be started before you can run in networked mode. (Read the file called running_database_server for guidance as to how to start and run the database server). 

2) When the server is started, run the application in networked mode by specifying no command line argument with the following command:

java <hyphen_sign>jar <path_and_filename> [<mode>]
    Where
       path_and_filename = runme.jar
       mode    is not specified (it equals nothing)

Hence command = java <hyphen_sign>jar runme.jar   

   


3) Press the return/enter key to run the application.

4) On successfully running, you should see a GUI with a table of hotel rooms from the centre to bottom with a search panel located at the top.





5. HOW TO SEARCH FOR HOTEL ROOM(S)

1) Type into the text field above the searchRoom button, the name or        location of the hotel room you want to find.

When you specify only a name the search will only match all hotel rooms with the specified name. 

When you specify only a location the search will only match all hotel rooms with the specified location.

When you specify both a name and location the search will only match all hotels rooms with such name and location simultaneously 

When you do not specify any name or location all the hotel rooms will be displayed

2) Press Enter key or click the searchRoom button to search for the room based on your search criteria.

The results are displayed in the Table below.
An empty or blank table means that no hotel room matches your search criteria.



6. HOW TO BOOK A HOTEL ROOM

Before you book, you must select a hotel room from the table.

Book a hotel room by following these steps:

1) Select the room that you want to book from the Table by clicking on 
that particular room. 
NOTE: A room must be selected first before you can book. Without a selected room you cannot book.

2) From the menu bar choose the Book menu

3) Click on the bookRoom menu item to book for the hotel room you have selected in the table

4) A window will pop_up requesting you to enter the ID you want to 
   use to book the room. This must be an eight(8) digit number.

5) Click on the Book button to book. Alternatively, you can cancel the booking by closing the window

6) A message box would be displayed to tell you whether or not your booking was successful.

5) After booking your room, you may book for another room by following the above procedures or you may now exit the application.

NOTE: This booking procedure is guided by a principle of 48 hour rule, where you can only book for a hotel room within 48 hours of the start of the room occupancy. From the records in the database, all the dates of the start of the room occupancy of each of the records has expired with this business 48 hour rule. Hence, for the purposes of this application, to successfully book a room (all things being equal), the system time of the JVM should be set to within 48 hours of the date of the particular room to book. Other than that booking shall never be successful.


7. EXITING THE APPLICATION
You may exit the application by choosing File from the Menu bar and selecting the Exit menu item 
OR
by clicking on the close button at the top right corner of the application window.





URLyBird HOTEL ROOM BOOKING APPLICATION

VERSION

Table Of Content

1.	JDK Version and Host Platform
1.0	JDK version
1.1	Host Platform
1.2	Hardware details




1 JDK Version and Host Platform



1.0 The JDK version used for this project was jdk1.6.0_11


The following is detailed information on the exact Java version:

java version "1.6.0_11"

Java(TM) 2 Runtime Environment, Standard Edition (build 1.6.0_11-b03)

Java HotSpot(TM) Client VM (build 11.0-b16, mixed mode, sharing)





1.1	Host Platform
1.2	
This project was developed and tested using Microsoft Windows XP Professional. 

The following details the exact operating system information:

Microsoft Windows XP Professional (5.1, Build 2600)
Version 2002
Service Pack 3

1.2 This project was developed and tested on a Toshiba laptop. 
The following details the exact hardware information:

Intel(R) Pentium(R) M processor 1200MHz
598MHz
496 MB of RAM
37.2GB HD

