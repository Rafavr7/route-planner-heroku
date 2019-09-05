package com.example.model;

import com.example.model.enums.TipoPOI;
import com.example.service.ArestaDAO;
import com.example.service.LinhasTransporteDAO;
import com.example.service.POIDao;
import com.example.service.ParagemDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Rota {
    /***************
    ** Static Values
    ***************/
    private static final int MAX_DISTANCE = 2000;
    private static final int EARTH_RADIUS = 6371;
    private static final int HEIGHT = 0;
    private static TipoPOI[] TIPOS_ARRAY = {TipoPOI.Desporto, TipoPOI.História, TipoPOI.Arte,
                             TipoPOI.Esplanada, TipoPOI.Restaurante, TipoPOI.Parque,
                             TipoPOI.Shopping};
    
    /***************
    ** Class Attributes
    ***************/
    private String id = "";
    private List<Paragem> paragensList;
    private List<LinhaTransporte> linhasTransporteList;
    private List<CustoTransporte> custos;
    private Integer duracao;
    private Double distancia;
    
    
    /***************
    ** Methods
    ***************/
    public Rota(List<Paragem> paragensList, List<LinhaTransporte> linhasTransporteList,
            List<CustoTransporte> custos, Integer duracao, Double distancia) {
        this.paragensList = paragensList;
        this.linhasTransporteList = linhasTransporteList;
        this.custos = custos;
        this.duracao = duracao;
        this.distancia = distancia;
    }
    
    public Rota(Rota rota1, Rota rota2) {
        if(rota1 == null || rota2 == null) {
            return;
        }
        
        List<Paragem> paragensRota1 = rota1.getParagensList();
        List<LinhaTransporte> linhasRota1 = rota1.getLinhasTransporteList();
        List<CustoTransporte> custosRota1 = rota1.getCustos();
        List<Paragem> paragensRota2 = rota2.getParagensList();
        List<LinhaTransporte> linhasRota2 = rota2.getLinhasTransporteList();
        List<CustoTransporte> custosRota2 = rota2.getCustos();
        
        paragensRota1.addAll(paragensRota2);
        linhasRota1.addAll(linhasRota2);
        custosRota1.addAll(custosRota2);
        
        paragensList = paragensRota1;
        linhasTransporteList = linhasRota1;
        custos = custosRota1;
        
        distancia = rota1.getDistancia() + rota2.getDistancia();
        duracao = rota1.getDuracao() + rota2.getDuracao();
    }
    
    public static Rota getRota(String nomeOrigem, String nomeDestino, int[] preferenciasPOIs) throws Exception {
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
        
        
        /** 3 - Sabendo as linhas de transporte é possível ir atrás de todas as
         * arestas e portanto descobrir também a lista de paragens a se ter em
         * conta.
         */
        HashSet<Paragem> paragensSet = new HashSet<>();
        HashSet<Aresta> arestasSet = new HashSet<>();
        for(LinhaTransporte linha : linhasTransporteSet) {
            ArestaDAO.getDataAndUpdateSets(linha, paragensSet, arestasSet);
        }
        
        ArrayList<Aresta> arestasList = new ArrayList<>(arestasSet);
        //ArrayList<LinhaTransporte> linhasList = new ArrayList<>(linhasTransporteSet);
        ArrayList<Paragem> paragensList = new ArrayList<>(paragensSet);
        
        /**
         * 4 - Com as informações adquiridas, chamamos um grafo para que se possa
         * tentar criar uma rota entre a Origem e o Destino. Caso não tenha sido
         * encontrada nenhuma rota possível entre os dois pontos, o processo termina
         * por aqui e podemos retornar null.
         */
        GrafoDjikstra grafo = new GrafoDjikstra(arestasList, paragensList);
        Rota rota = grafo.encontrarRota(origem, destino);
        if(rota == null) {
            return null;
        }
        
        
        /**
         * 5 - Passamos agora a encontrar os POIs referentes à última paragem da
         * rota obtida.
         */
        List<POI> poisList = new ArrayList<>();
        int quantidadeParagens = rota.getParagensList().size();
        Paragem lastParagem = rota.getParagensList().get(quantidadeParagens - 1);
        double paragemLat = lastParagem.getLatitude();
        double paragemLon = lastParagem.getLongitude();
        for(int x = 0; x < TIPOS_ARRAY.length; x++) {
            if(preferenciasPOIs[x] == 1) {
                List<POI> poisTempList = POIDao.getPoisByTipo(TIPOS_ARRAY[x]);
                for(POI p : poisTempList) {
                    if(calcularDistancia(paragemLat, paragemLon, p) <= MAX_DISTANCE) {
                        poisList.add(p);
                    }
                }
            }
        }
        lastParagem.setPoisList(poisList);
        
        /**
         * 6 - Retornar a rota
         */
        return rota;
    }
    
    private static double calcularDistancia(double paragemLat, double paragemLon, POI poi) {
        double poiLat = poi.getLatitude();
        double poiLon = poi.getLongitude();
        
        double distLat = Math.toRadians(paragemLat - poiLat);
        double distLon = Math.toRadians(paragemLon - poiLon);
        
        double a = Math.sin(distLat / 2) * Math.sin(distLat / 2)
                + Math.cos(Math.toRadians(poiLat)) * Math.cos(Math.toRadians(paragemLat))
                * Math.sin(distLon / 2) * Math.sin(distLon / 2);
        
        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * b * 1000;
        distance = Math.sqrt(Math.pow(distance, 2) + Math.pow(HEIGHT, 2));
        return distance;
    }
    
    /***************
    ** Getters And Setters
    ***************/
    public List<Paragem> getParagensList() {
        return paragensList;
    }

    public void setParagensList(List<Paragem> paragensList) {
        this.paragensList = paragensList;
    }

    public List<LinhaTransporte> getLinhasTransporteList() {
        return linhasTransporteList;
    }

    public void setLinhasTransporteList(List<LinhaTransporte> linhasTransporteList) {
        this.linhasTransporteList = linhasTransporteList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public List<CustoTransporte> getCustos() {
        return custos;
    }

    public void setCustos(List<CustoTransporte> custos) {
        this.custos = custos;
    }    
}
