package com.dinet.carga_pedido.domain.port.out;

import com.dinet.carga_pedido.domain.model.CargaIdempotenciaModel;

public interface CargaIdempotenciaRepository {

    boolean existsByIdempotencyKeyAndArchivoHash(String idempotencyKey, String archivoHash);
    void saveCargaIdempotencia(CargaIdempotenciaModel cargaIdempotencia);

}
