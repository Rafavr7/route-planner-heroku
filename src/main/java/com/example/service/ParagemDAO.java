package com.example.service;

import com.example.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class ParagemDAO {
    /***************
    ** Class Constants
    ***************/
    private static final String TABLE_NAME = "paragens";
    
    private static final String COLUMN_ID = "id";
    
    private static final String COLUMN_NOME = "nome";
    
    private static final String COLUMN_LATITUDE = "latitude";
    
    private static final String COLUMN_LONGITUDE = "longitude";
    
    private static final String COLUMN_ID_DISTRITO = "id_distrito";
    
    private static final String COLUMN_PEDIDOS = "pedidos";
    
    
    /***************
    ** Methods
    ***************/
    private ParagemDAO() {
        // EMPTY
    }
    
    public static ArrayList<String> getNomesParagensLike(String regex) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        ArrayList<String> nomes = new ArrayList<>();
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            String query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE nome LIKE '%" + regex + "%'";
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                String nome = resultSet.getString(COLUMN_NOME);
                nomes.add(nome);
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
        
        if(nomes.size() > 1) {
            Collections.sort(nomes, new Comparator<String>() {
                @Override
                public int compare(String nome1, String nome2) {
                    if(nome1.indexOf(regex) - nome2.indexOf(regex) == 0) {
                        return nome1.compareTo(nome2);
                    }
                    
                    return nome1.indexOf(regex) - nome2.indexOf(regex);
                }
            } );
        }
        
        return nomes;
    }
}
