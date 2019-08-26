package com.example.service;

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
}
