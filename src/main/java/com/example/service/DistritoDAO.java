package com.example.service;

import com.example.model.Distrito;
import com.example.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class DistritoDAO {
    /***************
    ** Class Constants
    ***************/
    private static final String TABLE_NAME = "distritos";
    
    private static final String COLUMN_ID = "id";
            
    private static final String COLUMN_NOME = "nome";
    
    
    /***************
    ** Methods
    ***************/
    private DistritoDAO() {
        // EMPTY
    }
    
    public static HashMap<Integer, Distrito> getDistritoByIdMap() throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        HashMap<Integer, Distrito> map = new HashMap<>();
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            String query = "SELECT * FROM " + TABLE_NAME;
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                Integer id = resultSet.getInt(COLUMN_ID);
                String nome = resultSet.getString(COLUMN_NOME);
                
                Distrito distrito = new Distrito(id, nome);
                map.put(id, distrito);
            }
        }
        catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela '"
                    + TABLE_NAME + "'");
            System.err.println(ex);
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
        
        return map;
    }
    
    public static Distrito getDistritoById(int idProcurado) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        Distrito distrito = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                    COLUMN_ID + " = " + idProcurado;
            resultSet = statement.executeQuery(query);
            
            if(resultSet.next()) {
                Integer id = resultSet.getInt(COLUMN_ID);
                String nome = resultSet.getString(COLUMN_NOME);
                
                distrito = new Distrito(id, nome);
            }
        }
        catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela '"
                    + TABLE_NAME + "'");
            System.err.println(ex);
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
}
