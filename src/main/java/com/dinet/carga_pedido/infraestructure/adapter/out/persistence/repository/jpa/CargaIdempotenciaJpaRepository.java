package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.CargaIdempotenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CargaIdempotenciaJpaRepository extends JpaRepository<CargaIdempotenciaEntity, UUID> {

    boolean existsByIdempotencyKeyAndArchivoHash(String idempotencyKey, String archivoHash);

}
