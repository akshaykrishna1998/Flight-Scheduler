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
public class Day {
    
    private Date date;
    private static PreparedStatement selectAllDays;
    private static DBConnection dbConnection;
    private static Connection connection;
    private PreparedStatement insertDay;
    
    public void setDay(Date adate)
    {
        date = adate;
    }
    
    public Day(Date adate)
    {
        date = adate;
    }
    
    public Date getDay()
    {
        return date;
    }
    
    public Date toDate()
    {
        return date;
    }
    
    public String toString()
    {
        return date.toString();
    }
    
    Day()
    {
        
    }
    
    public int addDay(Date adate)
    {
        int result = 0;
        
        try
        {
            connection = dbConnection.getDBConnection();
            insertDay = connection.prepareStatement("INSERT INTO DAY "+"(DATE)"+"VALUES (?)");
            
            insertDay.setDate(1, adate);
            
            result = insertDay.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            dbConnection.close();
        }
        return result;
    }
    
    public static List< Day > getAllDays() throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectAllDays = connection.prepareStatement("SELECT * FROM DAY");
        List<Day> results = null;
        ResultSet resultSet = null;
        try
        {
            resultSet = selectAllDays.executeQuery();
            results = new ArrayList< Day >();
            while(resultSet.next())
            {
                results.add(new Day(resultSet.getDate(1)));
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
    
}
