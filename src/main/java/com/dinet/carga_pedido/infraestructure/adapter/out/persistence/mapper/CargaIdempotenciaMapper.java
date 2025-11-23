package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper;

import com.dinet.carga_pedido.domain.model.CargaIdempotenciaModel;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.CargaIdempotenciaEntity;
import org.springframework.stereotype.Component;

@Component
public class CargaIdempotenciaMapper {

    public CargaIdempotenciaEntity domainToEntity(CargaIdempotenciaModel cargaIdempotencia) {
        return new CargaIdempotenciaEntity(
                cargaIdempotencia.getId(),
                cargaIdempotencia.getIdempotencyKey(),
                cargaIdempotencia.getArchivoHash(),
                cargaIdempotencia.getCreatedAt()
        );
    }

}
