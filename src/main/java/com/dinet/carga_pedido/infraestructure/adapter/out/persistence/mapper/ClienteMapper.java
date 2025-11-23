package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper;

import com.dinet.carga_pedido.domain.model.ClienteModel;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteMapper {

    public ClienteModel entityToDomain(ClienteEntity clienteEntity) {
        return new ClienteModel(
                clienteEntity.getId(),
                clienteEntity.isActivo()
        );
    }

    public List<ClienteModel> entitiesToDomains(List<ClienteEntity> clienteEntities) {
        List<ClienteModel> clientes = new ArrayList<>();
        for (ClienteEntity entity : clienteEntities) {
            clientes.add(entityToDomain(entity));
        }
        return clientes;
    }

}
