package com.example.model;

import com.example.service.LinhasTransporteDAO;
import com.example.service.ParagemDAO;

import java.util.List;
import java.util.HashSet;

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
    public Rota getRota(String nomeOrigem, String nomeDestino) throws Exception {
        /**
         * 1 - Buscar pelas paragens correspondentes aos nomes passados como
         * parâmetro. Primeiro tentar ir à tabela de paragens para buscá-las. Caso
         * não se encontre correspondência, verificar então na tabela de aliases
         * para as paragens que poderá fazer referência a um ID da tabela de
         * paragens.
         */
        Paragem origem = ParagemDAO.getParagemByNome(nomeOrigem);
        if(origem == null) {
            origem = ParagemDAO.getParagemByAlias(nomeOrigem);
        }
        
        if(origem == null) {
            return null;
        }
        
        Paragem destino = ParagemDAO.getParagemByNome(nomeDestino);
        if(destino == null) {
            destino = ParagemDAO.getParagemByAlias(nomeDestino);
        }
        
        if(destino == null) {
            return null;
        }
        
        
        /**
         * 2 - Sabendo os distritos das duas paragens, buscar as linhas de
         * transporte que atuem apenas nesses mesmos distritos.
         */
        HashSet<LinhaTransporte> linhasTransporteSet = new HashSet<>();
        int idDistrito = origem.getDistrito().getId();
        LinhasTransporteDAO.getAllLinhasByDistritoInHashSet(idDistrito, linhasTransporteSet);
        
        if(idDistrito != destino.getDistrito().getId()) {
            idDistrito = destino.getDistrito().getId();
            LinhasTransporteDAO.getAllLinhasByDistritoInHashSet(idDistrito, linhasTransporteSet);
        }
        
    }
}
