package com.oksmart.activitycontrol.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@Entity
public class Activity {

    @Id
    private String cliente;
    private Integer central;
    private Integer linha;
    private Integer endereco;
    private String tipo;

    public Activity() {}

    public Activity(String cliente, Integer central, Integer linha, Integer endereco, String tipo) {
        this.cliente = cliente;
        this.central = central;
        this.linha = linha;
        this.endereco = endereco;
        this.tipo = tipo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getCentral() {
        return central;
    }

    public void setCentral(Integer central) {
        this.central = central;
    }

    public Integer getLinha() {
        return linha;
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public Integer getEndereco() {
        return endereco;
    }

    public void setEndereco(Integer endereco) {
        this.endereco = endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}