package edu.slcc.asdv.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    public Database() {
        connection();
    }

    public Connection connection() //throws InstantiationException, IllegalAccessException
    {
        String databaseName = "productsforsale";
        String userName = "root";
        String password = "";
        String URL2 = "com.mysql.jdbc.Driver";
        Connection con = null;
        try {// Load Sun's jdbc driver
            Class.forName(URL2).newInstance();
            System.out.println("JDBC Driver loaded!");
        } catch (Exception e) // driver not found
        {
            System.err.println("Unable to load database driver");
            System.err.println("Details : " + e);
            return null;
        }
        String ip = "localhost"; //internet connection
        String url = "jdbc:mysql://" + ip + ":3306/" + databaseName;
        try {
            con = DriverManager.getConnection(url, userName, password);
            con.setReadOnly(false);
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
        System.out.println("connection successfull");
        return con;
    }

    public void closeDatabaseConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
