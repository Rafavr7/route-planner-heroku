package com.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class GrafoDjikstra {
    /***************
    ** Class Attributes
    ***************/
    private final HashMap<Paragem, ArrayList<Conexao>> mapAdjacencias = new HashMap<>();
    private final ArrayList<Node> unvisitedNodes = new ArrayList<>();
    private final HashMap<Paragem, Node> mapParagemNode = new HashMap<>();
    private final ArrayList<Aresta> arestasList;
    private final ArrayList<Paragem> paragensList;
    
    
    /***************
    ** Methods
    ***************/
    public GrafoDjikstra(ArrayList<Aresta> arestasList, 
            ArrayList<Paragem> paragensList) {
        
        this.arestasList = arestasList;
        this.paragensList = paragensList;
        
        initMapAdjacencias();
    }
    
    private void initMapAdjacencias() {
        for(Aresta aresta : arestasList) {
            Paragem p1 = aresta.getParagem1();
            Paragem p2 = aresta.getParagem2();
            int distancia = aresta.getCustoMetros();
            int tempo = aresta.getCustoMinutos();
            LinhaTransporte linha = aresta.getLinhaTransporte();
            
            ArrayList<Conexao> conexoesP1 = mapAdjacencias.get(p1);
            if(conexoesP1 == null) {
                conexoesP1 = new ArrayList<>();
            }
            Conexao c1 = new Conexao(p2, tempo, distancia, linha);
            conexoesP1.add(c1);
            mapAdjacencias.put(p1, conexoesP1);
            
            
            ArrayList<Conexao> conexoesP2 = mapAdjacencias.get(p2);
            if(conexoesP2 == null) {
                conexoesP2 = new ArrayList<>();
            }
            Conexao c2 = new Conexao(p1, tempo, distancia, linha);
            conexoesP2.add(c2);
            mapAdjacencias.put(p2, conexoesP2);
        }
    }
    
    public void initUnvisitedNodes(Paragem origem) {
        for(Paragem paragem : paragensList) {
            if(paragem.equals(origem)) {
                Node temp = new Node(paragem, 0);
                unvisitedNodes.add(temp);
                mapParagemNode.put(paragem, temp);
            }
            else {
                Node temp = new Node(paragem);
                unvisitedNodes.add(temp);
                mapParagemNode.put(paragem, temp);
            }
        }
    }
    
    public Rota encontrarRota(Paragem origem, Paragem destino) {
        initUnvisitedNodes(origem);
        Collections.sort(unvisitedNodes);
        
        ArrayList<Node> visitedNodes = new ArrayList<>();
        while(!unvisitedNodes.isEmpty()) {
            Node nodeAtual = unvisitedNodes.remove(unvisitedNodes.size() - 1);
            Paragem paragemAtual = nodeAtual.getParagem();
            int costSoFar = nodeAtual.getMenorCusto();
            
            if(costSoFar == Integer.MAX_VALUE) {
                break;
            }
            
            ArrayList<Conexao> conexoesParagemAtual = mapAdjacencias.get(paragemAtual);
            for(Conexao con : conexoesParagemAtual) {
                Paragem next = con.getNext();
                
                Node conNode = mapParagemNode.get(next);
                conNode.updateNode(nodeAtual, costSoFar + con.getTempo(), con.getLinhaTransporte(), con.getDistancia());
            }
            
            visitedNodes.add(nodeAtual);
            Collections.sort(unvisitedNodes);
        }
        
        Node nodeDestino = mapParagemNode.get(destino);
        if(!visitedNodes.contains(nodeDestino)) {
            // Caso o node referente à paragem de destino não tenha sido visitado
            // então não existe caminho possível entre os dois pontos
            return null;
        }
        
        /**
         * Experiência com Arestas no lugar de Paragens
         * TENTAR COLOCAR AS LINHAS DE TRANSPORTE DENTRO DA CLASSE NODE
         */
        ArrayList<Paragem> paragens = new ArrayList<>();
        ArrayList<LinhaTransporte> linhas = new ArrayList<>();
        int distanciaMetros = 0;
        int tempoMinutos = 0;
        Node nAtual = nodeDestino;
        while(true) {
            Paragem pTemp = nAtual.getParagem();
            LinhaTransporte lTemp = nAtual.getLinhaTransporte();
            paragens.add(pTemp);
            linhas.add(lTemp);
            
            distanciaMetros += nAtual.getMenorDistancia();
            tempoMinutos += nAtual.getMenorCusto();
            
            if(pTemp.equals(origem)) {
                break;
            }
            
            nAtual = nAtual.getPrevious();
        }
        
        Collections.reverse(paragens);
        Collections.reverse(linhas);
        Rota rota = new Rota(paragens, linhas, tempoMinutos, distanciaMetros / 1000.0);
        return rota;
    }
}
