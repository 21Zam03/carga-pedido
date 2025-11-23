package com.dinet.carga_pedido.shared.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FilaDetalle {

    Integer numeroLinea;
    String motivo;

}
