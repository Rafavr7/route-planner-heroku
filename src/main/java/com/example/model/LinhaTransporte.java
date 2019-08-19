package com.example.model;

import com.example.model.enums.TipoTransporte;
import com.example.utils.StringUtils;

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
