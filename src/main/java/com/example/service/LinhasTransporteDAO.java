package com.example.service;

import com.example.model.LinhaTransporte;
import com.example.model.enums.TipoTransporte;
import com.example.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashSet;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class LinhasTransporteDAO {
    /***************
    ** Class Constants
    ***************/
    private static final String TABLE_NAME = "linhas_transporte";
    
    private static final String COLUMN_ID = "id";
    
    private static final String COLUMN_NOME = "nome";
    
    private static final String COLUMN_TIPO_TRANSPORTE = "tipo_transporte";
    
    private static final String COLUMN_CUSTO_CENTIMOS = "custo_centimos";
    
    private static final String DISTRITOS_LINHAS_TABLE_NAME = "distritos_linhas_transporte";
    
    private static final String DISTRITOS_LINHAS_COLUMN_ID_LINHA = "id_linha_transporte";
    
    private static final String DISTRITOS_LINHAS_COLUMN_ID_DISTRITO = "id_distrito";
    
    
    /***************
    ** Methods
    ***************/
    private LinhasTransporteDAO() {
        // EMPTY
    }
    
    public static LinhaTransporte getLinhaTransporteById(int idProcurado) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        LinhaTransporte linhaTransporte = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ")
                    .append(TABLE_NAME)
                    .append(" WHERE ")
                    .append(COLUMN_ID)
                    .append(" = ")
                    .append(idProcurado);
            resultSet = statement.executeQuery(query.toString());
            
            if(resultSet.next()) {
                Integer id = resultSet.getInt(COLUMN_ID);
                String nome = resultSet.getString(COLUMN_NOME);
                TipoTransporte tipoTransporte = TipoTransporte.valueOf(resultSet.getString(COLUMN_TIPO_TRANSPORTE));
                Integer custoCentimos = resultSet.getInt(COLUMN_CUSTO_CENTIMOS);
                
                linhaTransporte = new LinhaTransporte(id, nome, tipoTransporte, custoCentimos);
            }
            
        }
        catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela "
                    + TABLE_NAME);
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
        
        return linhaTransporte;
    }
    
    public static void getAllLinhasByDistritoInHashSet(int idDistrito, HashSet<LinhaTransporte> linhas) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ")
                    .append(DISTRITOS_LINHAS_TABLE_NAME)
                    .append(" WHERE ")
                    .append(DISTRITOS_LINHAS_COLUMN_ID_DISTRITO)
                    .append(" = ")
                    .append(idDistrito);
            resultSet = statement.executeQuery(query.toString());
            
            while(resultSet.next()) {
                int idLinha = resultSet.getInt(DISTRITOS_LINHAS_COLUMN_ID_LINHA);
                LinhaTransporte temp = getLinhaTransporteById(idLinha);
                
                linhas.add(temp);
            }
            
        }
        catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados da tabela "
                    + TABLE_NAME);
            System.err.println(ex);
        }
    }
}
