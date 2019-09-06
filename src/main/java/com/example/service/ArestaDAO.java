package com.example.service;

import com.example.model.Aresta;
import com.example.model.LinhaTransporte;
import com.example.model.Paragem;
import com.example.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class ArestaDAO {
    /***************
    ** Class Constants
    ***************/
    private static final String TABLE_NAME = "arestas";
    
    private static final String COLUMN_ID_PARAGEM_1 = "id_paragem1";
    
    private static final String COLUMN_ID_PARAGEM_2 = "id_paragem2";
    
    private static final String COLUMN_ID_LINHA_TRANSPORTE = "id_linha_transporte";
    
    private static final String COLUMN_CUSTO_METROS = "custo_metros";
    
    private static final String COLUMN_CUSTO_MINUTOS = "custo_minutos";
    
    
    /***************
    ** Methods
    ***************/
    private ArestaDAO() {
        // EMPTY
    }
    
    public static void getDataAndUpdateSets(LinhaTransporte linhaTransporte,
            HashMap<Integer, Paragem> paragensMap, HashMap<Integer, Aresta> arestasMap) throws SQLException {
        
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ")
                    .append(TABLE_NAME)
                    .append(" WHERE ")
                    .append(COLUMN_ID_LINHA_TRANSPORTE)
                    .append(" = ")
                    .append(linhaTransporte.getId());
            
            resultSet = statement.executeQuery(query.toString());
            while(resultSet.next()) {
                Integer idParagem1 = resultSet.getInt(COLUMN_ID_PARAGEM_1);
                Integer idParagem2 = resultSet.getInt(COLUMN_ID_PARAGEM_2);
                Integer custoMetros = resultSet.getInt(COLUMN_CUSTO_METROS);
                Integer custoMinutos = resultSet.getInt(COLUMN_CUSTO_MINUTOS);
                
                Paragem p1 = paragensMap.get(idParagem1);
                Paragem p2 = paragensMap.get(idParagem2);
                
                if(p1 == null) {
                    p1 = ParagemDAO.getParagemById(idParagem1);
                    paragensMap.put(idParagem1, p1);
                }
                if(p2 == null) {
                    p2 = ParagemDAO.getParagemById(idParagem2);
                    paragensMap.put(idParagem2, p2);
                }
                
                Aresta a = new Aresta(p1, p2, linhaTransporte, custoMetros, custoMinutos);
                if(!arestasMap.containsKey(a.hashCode())) {
                    arestasMap.put(a.hashCode(), a);
                }
            }
        }
        catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela '"
                    + TABLE_NAME + "'");
            System.err.println(ex);
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
    }
}
