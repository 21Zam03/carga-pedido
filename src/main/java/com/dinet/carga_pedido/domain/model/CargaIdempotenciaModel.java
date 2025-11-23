package com.dinet.carga_pedido.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class CargaIdempotenciaModel {

    private UUID id;
    private String idempotencyKey;
    private String archivoHash;
    private LocalDateTime createdAt;

    public CargaIdempotenciaModel(UUID id, String idempotencyKey, String archivoHash, LocalDateTime createdAt) {
        this.id = id;
        this.idempotencyKey = idempotencyKey;
        this.archivoHash = archivoHash;
        this.createdAt = createdAt;
    }

    public CargaIdempotenciaModel() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }

    public String getArchivoHash() { return archivoHash; }
    public void setArchivoHash(String archivoHash) { this.archivoHash = archivoHash; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }


}
