package com.example.model;

import com.example.utils.StringUtils;

/**
 *
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class CustoTransporte {
    private double custo;
    private String tipoTransporte;
    
    
    public CustoTransporte(double custo, String tipoTransporte) {
        this.custo = custo;
        this.tipoTransporte = tipoTransporte;
    }
    
    @Override
    public String toString() {
        return StringUtils.toString(this.getClass(), this);
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public String getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }
}
