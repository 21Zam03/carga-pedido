package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.domain.model.ClienteModel;
import com.dinet.carga_pedido.domain.port.out.ClienteRepository;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.ClienteEntity;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper.ClienteMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteMapper clienteMapper;

    public ClienteRepositoryAdapter(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public boolean existsById(String idCliente) {
        return clienteJpaRepository.existsById(idCliente);
    }

    @Override
    public List<ClienteModel> findByIdIn(Set<String> ids) {
        List<ClienteEntity> clients = clienteJpaRepository.findByIdIn(ids);
        return clienteMapper.entitiesToDomains(clients);
    }

}
