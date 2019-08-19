package com.example.model;

import com.example.model.enums.TipoPOI;
import com.example.utils.StringUtils;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class POI {
    /***************
    ** Class Attributes
    ***************/
    private Integer id;
    private String nome;
    private String morada;
    private Double latitude;
    private Double longitude;
    private TipoPOI tipoPOI;
    
    
    /***************
    ** Methods
    ***************/
    public POI() {
        // EMPTY
    }
    
    public POI(Integer id, String nome, String morada, Double latitude,
            Double longitude, TipoPOI tipoPOI) {
        
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tipoPOI = tipoPOI;
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

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public TipoPOI getTipoPOI() {
        return tipoPOI;
    }

    public void setTipoPOI(TipoPOI tipoPOI) {
        this.tipoPOI = tipoPOI;
    }
    
}
