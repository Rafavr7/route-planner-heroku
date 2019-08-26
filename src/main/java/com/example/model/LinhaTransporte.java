package com.example.model;

import com.example.model.enums.TipoTransporte;
import com.example.utils.StringUtils;
import java.util.Objects;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class LinhaTransporte {
    /***************
    ** Class Attributes
    ***************/
    private Integer id;
    private String nome;
    private TipoTransporte tipoTransporte;
    private Integer custoCentimos;
    
    
    /***************
    ** Methods
    ***************/
    public LinhaTransporte() {
        // EMPTY
    }
    
    public LinhaTransporte(Integer id, String nome, TipoTransporte tipoTransporte, Integer custoCentimos) {
        this.id = id;
        this.nome = nome;
        this.tipoTransporte = tipoTransporte;
        this.custoCentimos = custoCentimos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.tipoTransporte);
        hash = 97 * hash + Objects.hashCode(this.custoCentimos);
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
        final LinhaTransporte other = (LinhaTransporte) obj;
        if (!Objects.equals(this.id, other.id)) {
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoTransporte getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(TipoTransporte tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public Integer getCustoCentimos() {
        return custoCentimos;
    }

    public void setCustoCentimos(Integer custoCentimos) {
        this.custoCentimos = custoCentimos;
    }    
}
