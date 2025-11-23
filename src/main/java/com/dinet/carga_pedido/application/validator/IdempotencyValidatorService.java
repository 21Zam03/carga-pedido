package com.dinet.carga_pedido.application.validator;

import com.dinet.carga_pedido.application.exception.ServiceException;
import com.dinet.carga_pedido.domain.port.out.CargaIdempotenciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IdempotencyValidatorService {

    private final CargaIdempotenciaRepository cargaIdempotenciaRepository;

    @Autowired
    public IdempotencyValidatorService(CargaIdempotenciaRepository cargaIdempotenciaRepository) {
        this.cargaIdempotenciaRepository = cargaIdempotenciaRepository;
    }

    public void validar(String idempotencyKey, String archivoHash) {
        if(cargaIdempotenciaRepository.existsByIdempotencyKeyAndArchivoHash(idempotencyKey, archivoHash)) {
            throw new ServiceException("El idempotency-key y archivo hash ya existen en base de datos");
        }
    }

}
