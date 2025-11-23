package com.dinet.carga_pedido.domain.model;

public class ZonaModel {

    private final String id;
    private final boolean soporteRegrigeracion;

    public ZonaModel(String id, boolean soporteRegrigeracion) {
        this.id = id;
        this.soporteRegrigeracion = soporteRegrigeracion;
    }

    public String getId() {
        return id;
    }

    public boolean isSoporteRegrigeracion() {
        return soporteRegrigeracion;
    }

}
