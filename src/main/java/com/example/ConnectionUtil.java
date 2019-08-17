package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    private static ConnectionUtil INSTANCE;
    
    private static final String DB_URL = "jdbc:mysql://eu-cdbr-west-02.cleardb.net:3306/";
    private static final String DB_NAME = "heroku_5d54c8545f9acd9";
    private static final String DB_USERNAME = "baa34b0023e475";
    private static final String DB_PASSWORD = "0e3aef29";

    private ConnectionUtil() {
        
    }
    
    public static ConnectionUtil getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConnectionUtil();
        }
        
        return INSTANCE;
    }
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USERNAME, DB_PASSWORD);
        }
        catch(Exception ex) {
            System.err.println("Erro!");
            ex.printStackTrace();
            return null;
        }
        
        return connection;
    }
}
