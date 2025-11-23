package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pedidos",
        indexes = {
                @Index(name = "idx_estado_fecha", columnList = "estado, fecha_entrega")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_numero_pedido", columnNames = "numero_pedido")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    private String numeroPedido;

    @Column(name = "cliente_id", nullable = false)
    private String clienteId;

    @Column(name = "zona_id", nullable = false)
    private String zonaId;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "requiere_refrigeracion")
    private boolean requiereRefrigeracion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
