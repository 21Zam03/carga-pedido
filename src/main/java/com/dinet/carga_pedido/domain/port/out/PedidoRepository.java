package com.dinet.carga_pedido.domain.port.out;

import com.dinet.carga_pedido.domain.model.PedidoModel;

import java.util.List;
import java.util.Set;

public interface PedidoRepository {

    PedidoModel save(PedidoModel pedidoModel);
    boolean existsByNumeroPedido(String numeroPedido);
    void saveAll(List<PedidoModel> batch);
    Set<String> findAllNumeroPedidos(Set<String> numeroPedidos);

}
