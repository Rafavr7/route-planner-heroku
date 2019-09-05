package com.example.model;

import com.example.utils.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class Paragem {
    /***************
    ** Class Attributes
    ***************/
    private Integer id;
    private String nome;
    private Double latitude;
    private Double longitude;
    private Distrito distrito;
    private Integer pedidos;
    private List<POI> poisList;
    
    
    /***************
    ** Methods
    ***************/
    public Paragem() {
        // EMPTY
    }
    
    public Paragem(Integer id, String nome, Double latitude, Double longitude,
            Distrito distrito, Integer pedidos) {
        
        this.id = id;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distrito = distrito;
        this.pedidos = pedidos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.nome);
        hash = 19 * hash + Objects.hashCode(this.latitude);
        hash = 19 * hash + Objects.hashCode(this.longitude);
        hash = 19 * hash + Objects.hashCode(this.distrito);
        hash = 19 * hash + Objects.hashCode(this.pedidos);
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
        final Paragem other = (Paragem) obj;
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

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public Integer getPedidos() {
        return pedidos;
    }

    public void setPedidos(Integer pedidos) {
        this.pedidos = pedidos;
    }
    
    public List<POI> getPoisList() {
        return poisList;
    }
    
    public void setPoisList(List<POI> poisList) {
        this.poisList = poisList;
    }
    
}
