package com.dinet.carga_pedido.shared.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResumenDto {

    private Integer totalProcesados;
    private Integer totalGuardados;
    private Integer totalConError;
    private List<FilaDetalle> filaDetalleList;
    private Map<String, Integer> erroresPorTipo;

}
