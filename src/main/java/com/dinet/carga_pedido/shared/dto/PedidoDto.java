package com.dinet.carga_pedido.shared.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PedidoDto {

    private String numeroPedido;
    private String clienteId;
    private LocalDate fechaEntrega;
    private String estado;
    private String zonaEntrega;
    private boolean requiereRefrigeracion;

}
