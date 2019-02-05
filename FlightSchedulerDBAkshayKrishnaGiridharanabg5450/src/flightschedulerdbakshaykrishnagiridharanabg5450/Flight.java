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

public class Flight {
    
    private String flightNumber;
    private int seats;
    private static PreparedStatement insertFlight;
    private static PreparedStatement selectAllFlights;
    private static PreparedStatement getFlightSeats;
    private static DBConnection dbConnection;
    private static Connection connection;
    private static PreparedStatement deleteFlight;
    
    public String toString()
    {
        return flightNumber;
    }
    
    public Flight()
    {
        
    }
    
    public int getSeatsAvailable(String flightName)
    {
        int seatsAvailable = 0;
        ResultSet resultSet = null;
        connection = dbConnection.getDBConnection();
        try
        {
            
            getFlightSeats = connection.prepareStatement("SELECT SEATS FROM FLIGHT WHERE flightName = ? "); 
            getFlightSeats.setString(1, flightName); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            seatsAvailable = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seatsAvailable;
    }
    
    
    
    public void deleteFlightRecord(String name) throws SQLException
    {
        connection = dbConnection.getDBConnection();
        deleteFlight = connection.prepareStatement("DELETE FROM FLIGHT WHERE FLIGHTNAME=?");
        deleteFlight.setString(1,name);
        deleteFlight.executeUpdate();
    }
    
    
    public int addFlight(String flight, int seats)
    {
        int result = 0;
        connection = dbConnection.getDBConnection();
        
        try
        {
            insertFlight = connection.prepareStatement("INSERT INTO FLIGHT"+"(FlightName,Seats)"+"VALUES (?,?)");
            insertFlight.setString(1, flight);
            insertFlight.setInt(2, seats);
            result = insertFlight.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            dbConnection.close();
        }
        return result;
    }
    
    public static List< Flight > getAllFlights() throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectAllFlights = connection.prepareStatement("SELECT * FROM FLIGHT");
        List<Flight> results = null;
        ResultSet resultSet = null;
        try
        {
            resultSet = selectAllFlights.executeQuery();
            results = new ArrayList< Flight >();
            while(resultSet.next())
            {
                results.add(new Flight(resultSet.getString(1),resultSet.getInt(2)));
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
    
    public Flight(String aflightNumber, int aseats)
    {
        flightNumber = aflightNumber;
        seats = aseats;
    }
        
    public String getFlightNumber()
    {
        return flightNumber;
    }
    
    public int getSeats()
    {
        return seats;
    }
}

