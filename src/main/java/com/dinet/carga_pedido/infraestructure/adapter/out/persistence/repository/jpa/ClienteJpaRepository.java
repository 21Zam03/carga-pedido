package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, String> {

    boolean existsById(String id);
    List<ClienteEntity> findByIdIn(Set<String> ids);

}
