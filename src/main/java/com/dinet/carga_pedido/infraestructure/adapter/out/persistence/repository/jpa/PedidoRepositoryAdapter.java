package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.domain.model.PedidoModel;
import com.dinet.carga_pedido.domain.port.out.PedidoRepository;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.PedidoEntity;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper.PedidoMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class PedidoRepositoryAdapter implements PedidoRepository {

    private final PedidoJpaRepository jpaRepository;
    private final PedidoMapper mapper;

    public PedidoRepositoryAdapter(PedidoJpaRepository jpaRepository, PedidoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public PedidoModel save(PedidoModel pedidoModel) {
        PedidoEntity pedidoEntity = mapper.domainToEntity(pedidoModel);
        PedidoEntity pedidoSaved = jpaRepository.save(pedidoEntity);
        return mapper.entityToDomain(pedidoSaved);
    }

    @Override
    public boolean existsByNumeroPedido(String numeroPedido) {
        return jpaRepository.existsByNumeroPedido(numeroPedido);
    }

    @Override
    public void saveAll(List<PedidoModel> batch) {
        jpaRepository.saveAll(mapper.domainListToEntityList(batch));
    }

    @Override
    public Set<String> findAllNumeroPedidos(Set<String> numeroPedidos) {
        return jpaRepository.findNumeroPedidosEncontrados(numeroPedidos);
    }

}
