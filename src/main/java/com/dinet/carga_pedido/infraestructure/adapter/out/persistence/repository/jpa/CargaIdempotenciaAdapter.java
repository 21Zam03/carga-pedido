package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.domain.model.CargaIdempotenciaModel;
import com.dinet.carga_pedido.domain.port.out.CargaIdempotenciaRepository;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.CargaIdempotenciaEntity;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper.CargaIdempotenciaMapper;
import org.springframework.stereotype.Component;

@Component
public class CargaIdempotenciaAdapter implements CargaIdempotenciaRepository {

    private final CargaIdempotenciaJpaRepository cargaIdempotenciaJpaRepository;
    private final CargaIdempotenciaMapper cargaIdempotenciaMapper;

    public CargaIdempotenciaAdapter(CargaIdempotenciaJpaRepository jpaRepository, CargaIdempotenciaMapper mapper) {
        this.cargaIdempotenciaJpaRepository = jpaRepository;
        this.cargaIdempotenciaMapper = mapper;
    }

    @Override
    public boolean existsByIdempotencyKeyAndArchivoHash(String idempotencyKey, String archivoHash) {
        return cargaIdempotenciaJpaRepository.existsByIdempotencyKeyAndArchivoHash(idempotencyKey, archivoHash);
    }

    @Override
    public void saveCargaIdempotencia(CargaIdempotenciaModel cargaIdempotencia) {
        CargaIdempotenciaEntity cargaIdempotenciaEntity = cargaIdempotenciaMapper.domainToEntity(cargaIdempotencia);
        cargaIdempotenciaJpaRepository.save(cargaIdempotenciaEntity);
    }

}
