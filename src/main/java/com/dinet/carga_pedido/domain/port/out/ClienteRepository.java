package com.dinet.carga_pedido.domain.port.out;

import com.dinet.carga_pedido.domain.model.ClienteModel;

import java.util.List;
import java.util.Set;

public interface ClienteRepository {

    boolean existsById(String idCliente);
    List<ClienteModel> findByIdIn(Set<String> ids);

}
