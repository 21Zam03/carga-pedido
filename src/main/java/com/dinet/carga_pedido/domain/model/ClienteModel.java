package com.dinet.carga_pedido.domain.model;

public class ClienteModel {

    private final String id;
    private final boolean activo;

    public ClienteModel(String id, boolean activo) {
        this.id = id;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public boolean isActivo() {
        return activo;
    }

}
