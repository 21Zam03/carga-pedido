package com.dinet.carga_pedido.domain.port.out;

import com.dinet.carga_pedido.domain.model.ZonaModel;

import java.util.List;
import java.util.Set;

public interface ZonaRepository {

    boolean existsById(String idZona);
    boolean getSoporteRefrigeracion(String idZona);
    List<ZonaModel> findByIdIn(Set<String> ids);

}
