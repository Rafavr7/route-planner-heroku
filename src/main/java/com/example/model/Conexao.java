package com.example.model;

import com.example.utils.StringUtils;

/**
 *
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Conexao {
    /***************
    ** Class Attributes
    ***************/
    private final Paragem next;
    private final int tempo;
    private final int distancia;
    private final LinhaTransporte linhaTransporte;
    
    
    /***************
    ** Methods
    ***************/
    public Conexao(Paragem next, int tempo, int distancia, LinhaTransporte linhaTransporte) {
        this.next = next;
        this.tempo = tempo;
        this.distancia = distancia;
        this.linhaTransporte = linhaTransporte;
    }
    
    @Override
    public String toString() {
        return StringUtils.toString(this.getClass(), this);
    }

    
    /***************
    ** Getters And Setters
    ***************/
    public Paragem getNext() {
        return next;
    }

    public int getTempo() {
        return tempo;
    }

    public int getDistancia() {
        return distancia;
    }

    public LinhaTransporte getLinhaTransporte() {
        return linhaTransporte;
    }    
}
