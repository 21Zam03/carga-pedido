package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "cargas_idempotencia",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idempotency_key", "archivo_hash"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CargaIdempotenciaEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;

    @Column(name = "archivo_hash", nullable = false)
    private String archivoHash;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
