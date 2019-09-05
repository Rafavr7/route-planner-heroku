package com.example.model;

import com.example.utils.StringUtils;

/**
 *
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Node implements Comparable<Node> {
    /***************
    ** Class Attributes
    ***************/
    private final Paragem paragem;
    private int menorCusto;
    private int menorDistancia;
    private LinhaTransporte linhaTransporte = null;
    private Node previous = null;
    
    
    /***************
    ** Methods
    ***************/
    public Node(Paragem paragem) {
        this.paragem = paragem;
        menorCusto = Integer.MAX_VALUE;
    }
    
    public Node(Paragem paragem, int menorCusto) {
        this.paragem = paragem;
        this.menorCusto = menorCusto;
    }
    
    public void updateNode(Node novoPrevious, int novoCusto, LinhaTransporte linhaTransporte, int menorDistancia) {
        if(menorCusto > novoCusto) {
            menorCusto = novoCusto;
            previous = novoPrevious;
            this.linhaTransporte = linhaTransporte;
            this.menorDistancia = menorDistancia;
        }
    }
    
    @Override
    public int compareTo(Node outro) {
        return outro.getMenorCusto() - menorCusto;
    }
    
    @Override
    public String toString() {
        return StringUtils.toString(this.getClass(), this);
    }

    
    /***************
    ** Getters And Setters
    ***************/
    public Paragem getParagem() {
        return paragem;
    }
    
    public int getMenorCusto() {
        return menorCusto;
    }

    public void setMenorCusto(int menorCusto) {
        this.menorCusto = menorCusto;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public LinhaTransporte getLinhaTransporte() {
        return linhaTransporte;
    }

    public void setLinhaTransporte(LinhaTransporte linhaTransporte) {
        this.linhaTransporte = linhaTransporte;
    }

    public int getMenorDistancia() {
        return menorDistancia;
    }

    public void setMenorDistancia(int menorDistancia) {
        this.menorDistancia = menorDistancia;
    }    
}
