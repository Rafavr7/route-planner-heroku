package com.example.model;

import com.example.utils.StringUtils;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Aresta {
    /***************
    ** Class Attributes
    ***************/
    private Integer id;
    private Paragem paragem1;
    private Paragem paragem2;
    private Integer custoMetros;
    private Integer custoMinutos;
    
    
    /***************
    ** Methods
    ***************/
    public Aresta() {
        // EMPTY
    }
    
    public Aresta(Integer id, Paragem paragem1, Paragem paragem2,
            Integer custoMetros, Integer custoMinutos) {
        
        this.id = id;
        this.paragem1 = paragem1;
        this.paragem2 = paragem2;
        this.custoMetros = custoMetros;
        this.custoMinutos = custoMinutos;
    }
    
    @Override
    public String toString() {
        return StringUtils.toString(this.getClass(), this);
    }

    
    /***************
    ** Getters and Setters
    ***************/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paragem getParagem1() {
        return paragem1;
    }

    public void setParagem1(Paragem paragem1) {
        this.paragem1 = paragem1;
    }

    public Paragem getParagem2() {
        return paragem2;
    }

    public void setParagem2(Paragem paragem2) {
        this.paragem2 = paragem2;
    }

    public Integer getCustoMetros() {
        return custoMetros;
    }

    public void setCustoMetros(Integer custoMetros) {
        this.custoMetros = custoMetros;
    }

    public Integer getCustoMinutos() {
        return custoMinutos;
    }

    public void setCustoMinutos(Integer custoMinutos) {
        this.custoMinutos = custoMinutos;
    }
    
    
}
