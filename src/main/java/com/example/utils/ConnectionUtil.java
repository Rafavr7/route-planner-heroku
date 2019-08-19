package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class ConnectionUtil {
    /***************
    ** Constants
    ***************/
    private static final String DB_URL = "jdbc:mysql://eu-cdbr-west-02.cleardb.net:3306/";
    
    private static final String DB_NAME = "heroku_5d54c8545f9acd9";
    
    private static final String DB_USERNAME = "baa34b0023e475";
    
    private static final String DB_PASSWORD = "0e3aef29";

    
    /***************
    ** Methods
    ***************/
    private ConnectionUtil() {
        
    }
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USERNAME, DB_PASSWORD);
        }
        catch(ClassNotFoundException | SQLException ex) {
            System.err.println("Erro ao conectar-se com a base de dados '"
                    + DB_NAME + "' (Route Planner)");
            System.err.println(ex.getMessage() + ex);
            return null;
        }
        
        return connection;
    }
}
