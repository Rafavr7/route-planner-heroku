package com.example.model;

import com.example.service.ParagemDAO;
import java.util.List;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Rota {
    /***************
    ** Class Attributes
    ***************/
    private List<Paragem> paragens;
    
    
    /***************
    ** Methods
    ***************/
    public Rota(String nomeOrigem, String nomeDestino) throws Exception {
        /**
         * 1 - Buscar pelas paragens correspondentes aos nomes passados como
         * parâmetro. Primeiro tentar ir à tabela de paragens para buscá-las. Caso
         * não se encontre correspondência, verificar então na tabela de aliases
         * para as paragens que poderá fazer referência a um ID da tabela de
         * paragens.
         */
        Paragem origem = ParagemDAO.getParagemByNome(nomeOrigem);
        if(origem == null) {
            
        }
    }
}
