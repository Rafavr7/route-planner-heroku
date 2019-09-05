package com.example.service;

import com.example.model.POI;
import com.example.model.enums.TipoPOI;
import com.example.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class POIDao {
    /***************
    ** Class Constants
    ***************/
    private static final String TABLE_NAME = "pois";
    
    private static final String COLUMN_ID = "id";
    
    private static final String COLUMN_NOME = "nome";
    
    private static final String COLUMN_MORADA = "morada";
    
    private static final String COLUMN_LATITUDE = "latitude";
    
    private static final String COLUMN_LONGITUDE = "longitude";
    
    private static final String COLUMN_TIPO = "tipo";
    
    
    /***************
    ** Methods
    ***************/
    private POIDao() {
        // EMPTY
    }
    
    public static List<POI> getPoisByTipo(TipoPOI tipoProcurado) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        List<POI> poisList = new ArrayList<>();
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ")
                    .append(TABLE_NAME)
                    .append(" WHERE ")
                    .append(COLUMN_TIPO)
                    .append(" = '")
                    .append(tipoProcurado.toString())
                    .append("'");
            
            resultSet = statement.executeQuery(query.toString());
            while(resultSet.next()) {
                Integer id = resultSet.getInt(COLUMN_ID);
                String nome = resultSet.getString(COLUMN_NOME);
                String morada = resultSet.getString(COLUMN_MORADA);
                Double latitude = resultSet.getDouble(COLUMN_LATITUDE);
                Double longitude = resultSet.getDouble(COLUMN_LONGITUDE);
                
                POI p = new POI(id, nome, morada, latitude, longitude, tipoProcurado);
                poisList.add(p);
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
        
        return poisList;
    }
}
