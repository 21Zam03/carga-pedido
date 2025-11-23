package com.dinet.carga_pedido.domain.port.out;

import com.dinet.carga_pedido.shared.dto.PedidoDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public interface FileReader {

    List<PedidoDto> read(InputStream input) throws Exception;
    void readStream(InputStream input, Consumer<PedidoDto> consumidor) throws IOException;

}
