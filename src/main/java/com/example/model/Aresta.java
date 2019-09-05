package com.example.model;

import com.example.utils.StringUtils;
import java.util.Objects;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Aresta {
    /***************
    ** Class Attributes
    ***************/
    private Paragem paragem1;
    private Paragem paragem2;
    private LinhaTransporte linhaTransporte;
    private Integer custoMetros;
    private Integer custoMinutos;
    
    
    /***************
    ** Methods
    ***************/
    public Aresta() {
        // EMPTY
    }
    
    public Aresta(Paragem paragem1, Paragem paragem2,
            LinhaTransporte linhaTransporte, Integer custoMetros, Integer custoMinutos) {
        
        this.paragem1 = paragem1;
        this.paragem2 = paragem2;
        this.linhaTransporte = linhaTransporte;
        this.custoMetros = custoMetros;
        this.custoMinutos = custoMinutos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        int idParagem1 = paragem1.getId();
        int idParagem2 = paragem2.getId();
        
        hash = 79 * hash + Objects.hashCode(idParagem1);
        hash = 79 * hash + Objects.hashCode(idParagem2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aresta other = (Aresta) obj;
        if (!Objects.equals(this.paragem1, other.paragem1)) {
            return false;
        }
        if (!Objects.equals(this.paragem2, other.paragem2)) {
            return false;
        }
        return true;
    }    
    
    @Override
    public String toString() {
        return StringUtils.toString(this.getClass(), this);
    }

    
    /***************
    ** Getters and Setters
    ***************/
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
    
    public LinhaTransporte getLinhaTransporte() {
        return linhaTransporte;
    }
    
    public void setLinhaTransporte(LinhaTransporte linhaTransporte) {
        this.linhaTransporte = linhaTransporte;
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
