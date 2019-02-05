/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerdbakshaykrishnagiridharanabg5450;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akshay Krishna
 */
public class Booking
{
    private String passengerName;
    private Date date;
    private String flightNumber;
    private PreparedStatement insertBooking;
    private static PreparedStatement selectCustomersBooked;
    private static PreparedStatement selectCustomerDayBooked;
    private static PreparedStatement deleteBooking;
    private PreparedStatement getFlightSeats;
    private static DBConnection dbConnection;
    private static Connection connection;
    
    
    
    public Date getDay()
    {
        return date;
    }
      
    
    public String getFlightNumber()
    {
        return flightNumber;
    }
    
    public String getPassenger()
    {
        return passengerName;
    }
    
    
    public Booking() 
    {
        
        
    }
    
    public Booking(String aname, String aflightNo, Date adate) 
    {
        passengerName = aname;
        date = adate;
        flightNumber =aflightNo;
        
    }
    
    public void deleteBookingRecord(String name, String aflightNumber, Date adate) throws SQLException
    {
        connection = dbConnection.getDBConnection();
        deleteBooking = connection.prepareStatement("DELETE FROM BOOKING WHERE NAME=? AND DAY=?");
        deleteBooking.setString(1,name);
        deleteBooking.setDate(2, adate);
        deleteBooking.executeUpdate();
        
    }
    
    public static List< Booking > getCustomerDayBooked(String Name, Date adate) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomerDayBooked = connection.prepareStatement("SELECT * FROM BOOKING WHERE NAME=? AND DAY=?");
        List<Booking> results = null;
        ResultSet resultSet = null;
        try
        {
            
            selectCustomerDayBooked.setString(1, Name);
            selectCustomerDayBooked.setDate(2, adate);
            resultSet = selectCustomerDayBooked.executeQuery();
            results = new ArrayList< Booking >();
            while(resultSet.next())
            {
                results.add(new Booking(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                dbConnection.close();
            }
        }
        return results;        
    }
    
    public int getSeatsBooked(String flightName, Date adate)
    {
        int seatsBooked = 0;
        ResultSet resultSet;
        connection = dbConnection.getDBConnection();
        try
        {
            connection = dbConnection.getDBConnection();
        getFlightSeats = connection.prepareStatement("select count(flightNumber) from booking where flightNumber = ? and day = ?");
             
            getFlightSeats.setString(1, flightName); getFlightSeats.setDate(2, adate); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            seatsBooked = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seatsBooked;
    }
    
    public String ToString()
    {
        return passengerName;
    }
    
    public String ToString1()
    {
        return ""+passengerName+","+flightNumber+","+date;
    }
    
    public static List< Booking > getCustomersBooked(String flightName, Date adate) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomersBooked = connection.prepareStatement("SELECT * FROM BOOKING WHERE FLIGHTNUMBER=? AND DAY=?");
        List<Booking> results = null;
        ResultSet resultSet = null;
        try
        {
            
            selectCustomersBooked.setString(1, flightName);
            selectCustomersBooked.setDate(2, adate);
            resultSet = selectCustomersBooked.executeQuery();
            results = new ArrayList< Booking >();
            while(resultSet.next())
            {
                results.add(new Booking(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                dbConnection.close();
            }
        }
        return results;        
    }
    
    
    
    public static List< Booking > getCustomersBooked(String passengerName) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomersBooked = connection.prepareStatement("SELECT * FROM BOOKING WHERE NAME=?");
        List<Booking> results = null;
        ResultSet resultSet = null;
        try
        {
            
            selectCustomersBooked.setString(1, passengerName);
            
            resultSet = selectCustomersBooked.executeQuery();
            results = new ArrayList< Booking >();
            while(resultSet.next())
            {
                results.add(new Booking(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                dbConnection.close();
            }
        }
        return results;        
    }
    
    
    public int addBooking(String aname, String aflightNumber, Date adate)
    {
        int result = 0;
        
        try
        {
            connection = dbConnection.getDBConnection();
            insertBooking = connection.prepareStatement("INSERT INTO BOOKING "+"(Name,FlightNumber,Day)"+"VALUES (?,?,?)");
            
            insertBooking.setString(1, aname);
            insertBooking.setString(2, aflightNumber);
            insertBooking.setDate(3, adate);
            result = insertBooking.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            dbConnection.close();
        }
        return result;
    }
     
    
}

