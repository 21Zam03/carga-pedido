package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, UUID> {

    boolean existsByNumeroPedido(String numeroPedido);

    @Query("SELECT p.numeroPedido FROM PedidoEntity p")
    Set<String> findAllNumeroPedidos();

}
