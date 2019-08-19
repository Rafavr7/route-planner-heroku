package com.example.model;

import com.example.utils.StringUtils;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Distrito {
    /***************
    ** Class Attributes
    ***************/
    private Integer id;
    private String nome;
    
    
    /***************
    ** Methods
    ***************/
    public Distrito() {
        // EMPTY
    }
    
    public Distrito(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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
}
