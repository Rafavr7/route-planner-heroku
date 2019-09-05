package com.example.service;

import com.example.model.Distrito;
import com.example.model.Paragem;
import com.example.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

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
    
    private static final String ALIAS_TABLE_NAME = "paragem_alias";
    
    private static final String ALIAS_TABLE_COLUMN_ALIAS = "alias";
    
    private static final String ALIAS_TABLE_NOME_PARAGEM = "nome_paragem";
    
    private static final String LINHAS_PARAGENS_TABLE_NAME = "paragens_linhas_transporte";
    
    private static final String LINHAS_PARAGENS_COLUMN_PARAGEM_ID = "id_paragem";
    
    private static final String LINHAS_PARAGENS_COLUMN_LINHA_ID = "id_linha_transporte";
            
            
    /***************
    ** Methods
    ***************/
    private ParagemDAO() {
        // EMPTY
    }
    
    /**
     * 
     * @param regex
     * @return
     *      lista com sugestões de nomes de paragem que respeitem a regex passada
     * como parâmetro
     * @throws SQLException 
     */
    public static ArrayList<String> getNomesParagensLike(String regex) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        ArrayList<String> nomes = new ArrayList<>();
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            String query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_NOME + " LIKE '%" + regex + "%'";
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
    
    public static Paragem getParagemById(int idProcurado) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        Paragem paragem = null;
        
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
                Double latitude = resultSet.getDouble(COLUMN_LATITUDE);
                Double longitude = resultSet.getDouble(COLUMN_LONGITUDE);
                Integer idDistrito = resultSet.getInt(COLUMN_ID_DISTRITO);
                Integer pedidos = resultSet.getInt(COLUMN_PEDIDOS);
                
                Distrito distrito = DistritoDAO.getDistritoById(idDistrito);
                paragem = new Paragem(id, nome, latitude, longitude, distrito, pedidos);
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
        
        return paragem;
    }
    
    public static Paragem getParagemByNome(String nomeProcurado) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        Paragem paragem = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            String upperNome = nomeProcurado.toUpperCase();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE UPPER(" 
                    + COLUMN_NOME + ") = '" + upperNome + "'";
            
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                Integer id = resultSet.getInt(COLUMN_ID);
                String nome = resultSet.getString(COLUMN_NOME);
                Double latitude = resultSet.getDouble(COLUMN_LATITUDE);
                Double longitude = resultSet.getDouble(COLUMN_LONGITUDE);
                Integer idDistrito = resultSet.getInt(COLUMN_ID_DISTRITO);
                Integer pedidos = resultSet.getInt(COLUMN_PEDIDOS);
                
                Distrito distrito = DistritoDAO.getDistritoById(idDistrito);
                paragem = new Paragem(id, nome, latitude, longitude, distrito, pedidos);
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
        
        return paragem;
    }
    
    public static Paragem getParagemByAlias(String alias) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        Paragem paragem = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            String upperAlias = alias.toUpperCase();
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ")
                    .append(ALIAS_TABLE_NAME)
                    .append(" WHERE UPPER(")
                    .append(ALIAS_TABLE_COLUMN_ALIAS)
                    .append(") = '")
                    .append(upperAlias)
                    .append("'");
            resultSet = statement.executeQuery(query.toString());
            
            if(resultSet.next()) {
                String nomeParagem = resultSet.getString(ALIAS_TABLE_NOME_PARAGEM);
                paragem = getParagemByNome(nomeParagem);
            }
            else {
                return null;
            }
        }
        catch(SQLException ex) {
            System.err.println("Não foi possível aceder aos dados das tabelas");
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
        
        return paragem;
    }
    
    public static void getParagensByLinhaTransporteInHashSet(int idLinhaTransporte, HashSet<Paragem> paragensSet) throws SQLException {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            con = ConnectionUtil.getConnection();
            statement = con.createStatement();
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ")
                    .append(LINHAS_PARAGENS_TABLE_NAME)
                    .append(" WHERE ")
                    .append(LINHAS_PARAGENS_COLUMN_LINHA_ID)
                    .append(" = ")
                    .append(idLinhaTransporte);
            resultSet = statement.executeQuery(query.toString());
            
            while(resultSet.next()) {
                int idParagem = resultSet.getInt(LINHAS_PARAGENS_COLUMN_PARAGEM_ID);
                Paragem temp = getParagemById(idParagem);
                
                paragensSet.add(temp);
            }
            
        }
        catch(SQLException ex) {
            System.err.println("Não foi possível aceder ao dados da tabela "
                    + TABLE_NAME);
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
