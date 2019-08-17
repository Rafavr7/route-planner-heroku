package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class DataBaseConnector {
    private static DataBaseConnector INSTANCE;
    
    private static final String DB_URL = "jdbc:mysql://eu-cdbr-west-02.cleardb.net:3306/";
    private static final String DB_NAME = "heroku_5d54c8545f9acd9";
    private static final String DB_USERNAME = "baa34b0023e475";
    private static final String DB_PASSWORD = "0e3aef29";
    
    private static final String DISTRITOS_TABLE_NAME = "distritos";
    
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    
    private DataBaseConnector() {
        System.out.println("Iniciar a conexão com a base de dados...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USERNAME, DB_PASSWORD);
            statement = connection.createStatement();
            System.out.println("Conexão com '" + DB_NAME + "' (Route Planner) "
                    + "bem sucedida!");
            
        }
        catch(ClassNotFoundException ex) {
            System.err.println("Erro ao conectar com a Base de Dados!");
            ex.printStackTrace();
        }
        catch(SQLException ex) {
            System.err.println("Erro ao conectar com a Base de Dados!");
            ex.printStackTrace();
        }
    }
    
    public static DataBaseConnector getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataBaseConnector();
        }
        
        return INSTANCE;
    }
    
    
    /**
     * 
     * @return 
     */
    public ArrayList<Distrito> listDistritos() {
        ArrayList<Distrito> distritos = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM " + DISTRITOS_TABLE_NAME;
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                
                Distrito d = new Distrito(id, nome);
                distritos.add(d);
            }
            
        }catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela"
                    + " '" + DISTRITOS_TABLE_NAME + "'");
            ex.printStackTrace();
            return null;
        }
        
        return distritos;
    }
    
    
    public Distrito getDistritoById(Integer distritoId) {
        Distrito distrito = null;
        
        try {
            String query = "SELECT * FROM " + DISTRITOS_TABLE_NAME + " WHERE id= '"
                    + distritoId + "'";
            resultSet = statement.executeQuery(query);
            
            if(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                
                distrito = new Distrito(id, nome);
                
            }
            
        }catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela"
                    + " '" + DISTRITOS_TABLE_NAME + "'");
            ex.printStackTrace();
            return null;
        }
        
        return distrito;
    }
    
}
