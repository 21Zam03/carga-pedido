package com.dinet.carga_pedido.application.port.in;

import com.dinet.carga_pedido.shared.dto.ResumenDto;
import org.springframework.web.multipart.MultipartFile;

public interface CargaPedidoService {

    ResumenDto cargarPedidosDesdeCsv(MultipartFile file, String idempotencyKey) throws Exception;

}
