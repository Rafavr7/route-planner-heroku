package com.example.database;

import com.example.utils.ConnectionUtil;
import com.example.model.Distrito;
import java.sql.Connection;
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
    private static final String PARAGENS_TABLE_NAME = "paragens";
    
    
    private DataBaseConnector() {
        
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
    public ArrayList<Distrito> listDistritos() throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        ArrayList<Distrito> distritos = new ArrayList<>();
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
                        
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
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(con != null) {
                con.close();
            }
        }
        
        return distritos;
    }
    
    
    public Distrito getDistritoById(Integer distritoId) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        Distrito distrito = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
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
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(con != null) {
                con.close();
            }
        }
        
        return distrito;
    }
    
    /**
     * 
     * @return
     *      lista com nomes de paragens que contém a regex
     */
    public ArrayList<String> getNomesParagensLike(String regex) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        ArrayList<String> nomes = new ArrayList<>();
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            String query = "SELECT * FROM " + PARAGENS_TABLE_NAME +
                    " WHERE nome LIKE '%" + regex + "%'";
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                String nome = resultSet.getString("nome");
                nomes.add(nome);
            }
            
        }catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela '"
                    + PARAGENS_TABLE_NAME + "'");
            ex.printStackTrace();
            return null;
        }
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(con != null) {
                con.close();
            }
        }
        
        return nomes;
    }
    
}
