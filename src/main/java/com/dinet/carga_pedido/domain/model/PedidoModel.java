package com.dinet.carga_pedido.domain.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class PedidoModel {

    private String numeroPedido;
    private String clienteId;
    private String estado;
    private LocalDate fechaEntrega;
    private String zonaEntrega;
    private boolean requiereRefrigeracion;

    public PedidoModel(String numeroPedido, String clienteId, String estado,
                       LocalDate fechaEntrega, String zonaEntrega, boolean requiereRefrigeracion) {
        this.numeroPedido = numeroPedido;
        this.clienteId = clienteId;
        this.estado = estado;
        this.fechaEntrega = fechaEntrega;
        this.zonaEntrega = zonaEntrega;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public String getNumeroPedido() { return numeroPedido; }
    public String getClienteId() { return clienteId; }
    public String getEstado() { return estado; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public String getZonaEntrega() { return zonaEntrega; }
    public boolean isRequiereRefrigeracion() { return requiereRefrigeracion; }

    public void validarNegocio() {
        validarNumeroPedido();
        validarEstado();
        validarFechaEntrega();
    }

    private void validarNumeroPedido() {
        if (numeroPedido == null ||
                !numeroPedido.matches("^[a-zA-Z0-9]+$")) {
            throw new RuntimeException("NUMERO_PEDIDO_INVALIDO");
        }
    }

    private void validarEstado() {
        if (!List.of("PENDIENTE", "CONFIRMADO", "ENTREGADO")
                .contains(estado)) {
            throw new RuntimeException("ESTADO_INVALIDO");
        }
    }

    private void validarFechaEntrega() {
        if (fechaEntrega== null) {
            throw new RuntimeException("FECHA_INVALIDA");
        }

        ZoneId zonaLima = ZoneId.of("America/Lima");
        LocalDate hoyLima = LocalDate.now(zonaLima);

        if (fechaEntrega.isBefore(hoyLima)) {
            throw new RuntimeException("FECHA_ENTREGA_PASADA");
        }
    }

}