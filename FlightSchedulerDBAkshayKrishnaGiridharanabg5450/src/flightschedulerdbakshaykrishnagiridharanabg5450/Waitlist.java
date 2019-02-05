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
public class Waitlist {
    private String passengerName;
    private Date date;
    private String flightNumber;
    private PreparedStatement insertWaitlist;
    private PreparedStatement getPosition;
    private PreparedStatement getFirstWaitList;
    private PreparedStatement updatePositions;
    private static PreparedStatement selectCustomerDayWaitlisted;
    private PreparedStatement deleteWaitlist;
    private int position;
    private static DBConnection dbConnection;
    private static PreparedStatement selectCustomersBooked;
    private static PreparedStatement selectCustomersWBooked;
    private static Connection connection;
    
    
    public void addWaitList(String apassengerName, Date adate, String aflightNumber )
    {
        passengerName = apassengerName;
        date = adate;
        flightNumber = aflightNumber;
    }
    
    public Date getDay()
    {
        return date;
    }
      
    
    public String getFlightNumber()
    {
        return flightNumber;
    }
    
    public void updateWaitlistPositions(String aflightName, Date aday, int aposition) throws SQLException
    {
        connection = dbConnection.getDBConnection();
        updatePositions = connection.prepareStatement("UPDATE WAITLIST SET POSITION = (POSITION-1) WHERE FLIGHTNAME = ? AND DAY = ? AND POSITION>?");
    
        updatePositions.setString(1, aflightName);
        updatePositions.setDate(2, aday);
        updatePositions.setInt(3, aposition);
        updatePositions.executeUpdate();
    
    }
    
    public String getFirstWaitList(String aflightName, Date aday) throws SQLException
    {
        String name="";
        connection = dbConnection.getDBConnection();
        getFirstWaitList = connection.prepareStatement("SELECT NAME FROM WAITLIST WHERE FLIGHTNAME = ? AND DAY = ? AND POSITION = 1");
        ResultSet resultSet = null;
        try
        {
            getFirstWaitList.setString(1, aflightName);
            getFirstWaitList.setDate(2, aday);
            resultSet = getFirstWaitList.executeQuery();
            while(resultSet.next())
            {
                name = resultSet.getString(1);
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
        deleteWaitlistRecord(name,aflightName,aday);
        updateWaitlistPositions(aflightName, aday, 1);
        return name;
    }
    
    public int getPosition()
    {
        return position;
    }
    
    public Waitlist(String aname, String aflight, Date adate, int aposition)
    {
        passengerName=aname;
    date=adate;
    flightNumber=aflight;
    position=aposition;
    }
    
    public void deleteWaitlistRecord(String name, String aflightNumber, Date adate) throws SQLException
    {
        connection = dbConnection.getDBConnection();
        deleteWaitlist = connection.prepareStatement("DELETE FROM WAITLIST WHERE NAME=? AND DAY=?");
        deleteWaitlist.setString(1,name);
        deleteWaitlist.setDate(2, adate);
        deleteWaitlist.executeUpdate();
        
    }
    
    public void deleteWaitlistRecord(String aflightNumber) throws SQLException
    {
        connection = dbConnection.getDBConnection();
        deleteWaitlist = connection.prepareStatement("DELETE FROM WAITLIST WHERE FLIGHTNAME=?");
        deleteWaitlist.setString(1,aflightNumber);
        deleteWaitlist.executeUpdate();
        
    }
    
    public static List< Waitlist > getCustomerDayWaitlisted(String Name, Date adate) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomerDayWaitlisted = connection.prepareStatement("SELECT * FROM WAITLIST WHERE NAME=? AND DAY=?");
        List<Waitlist> results = null;
        ResultSet resultSet = null;
        try
        {
            
            selectCustomerDayWaitlisted.setString(1, Name);
            selectCustomerDayWaitlisted.setDate(2, adate);
            resultSet = selectCustomerDayWaitlisted.executeQuery();
            results = new ArrayList< Waitlist >();
            while(resultSet.next())
            {
                results.add(new Waitlist(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3),resultSet.getInt(4)));
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
    
    
    public int getWaitlistPosition(String flightName, Date adate)
    {
        int position = 1;
        ResultSet resultSet;
        connection = dbConnection.getDBConnection();
        try
        {
            connection = dbConnection.getDBConnection();
            getPosition = connection.prepareStatement("SELECT COUNT(flightName) from WAITLIST where flightName = ? and day = ?");
             
            getPosition.setString(1, flightName); getPosition.setDate(2, adate); 
            resultSet = getPosition.executeQuery(); 
            resultSet.next(); 
            position = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return position+1;
    }
    
    public int addBooking(String aname, String aflightNumber, Date adate)
    {
        int result = 0;
        int r=0;
        try
        {
            connection = dbConnection.getDBConnection();
            insertWaitlist = connection.prepareStatement("INSERT INTO WAITLIST "+"(Name,FlightName,Day,Position)"+"VALUES (?,?,?,?)");
            r = getWaitlistPosition(aflightNumber,adate);
            insertWaitlist.setString(1, aname);
            insertWaitlist.setString(2, aflightNumber);
            insertWaitlist.setDate(3, adate);
            insertWaitlist.setInt(4, r);
            result = insertWaitlist.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            dbConnection.close();
        }
        return r;
    }
    
    
    public String ToString()
    {
        return ""+passengerName+","+flightNumber+","+position;
    }
    
    public String ToString1()
    {
        return ""+passengerName+","+flightNumber+","+date+","+position;
    }
    
    public static List< Waitlist > getCustomersBooked(Date adate) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomersBooked = connection.prepareStatement("SELECT * FROM WAITLIST WHERE DAY=?");
        List<Waitlist> results = null;
        ResultSet resultSet = null;
        try
        {
            
            
            selectCustomersBooked.setDate(1, adate);
            resultSet = selectCustomersBooked.executeQuery();
            results = new ArrayList< Waitlist >();
            while(resultSet.next())
            {
                results.add(new Waitlist(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3),resultSet.getInt(4)));
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
    
    
    public static List< Waitlist > getCustomersBooked(String passengerName) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomersWBooked = connection.prepareStatement("SELECT * FROM WAITLIST WHERE NAME = ?");
        List<Waitlist> results = null;
        ResultSet resultSet = null;
        try
        {
            
            
            selectCustomersWBooked.setString(1, passengerName);
            resultSet = selectCustomersWBooked.executeQuery();
            results = new ArrayList< Waitlist >();
            while(resultSet.next())
            {
                results.add(new Waitlist(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3),resultSet.getInt(4)));
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
    
    public Waitlist()
    {
        
    }
    
    public String getPassenger()
    {
        return passengerName;
    }
    
}

