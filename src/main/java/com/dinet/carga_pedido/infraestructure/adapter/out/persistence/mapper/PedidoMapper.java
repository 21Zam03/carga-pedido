package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper;

import com.dinet.carga_pedido.domain.model.PedidoModel;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.PedidoEntity;
import com.dinet.carga_pedido.shared.dto.PedidoDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public PedidoEntity domainToEntity(PedidoModel pedido) {
        return new PedidoEntity(
                null,
                pedido.getNumeroPedido(),
                pedido.getClienteId(),
                pedido.getZonaEntrega(),
                pedido.getFechaEntrega(),
                pedido.getEstado(),
                pedido.isRequiereRefrigeracion(),
                null,
                null
        );
    }

    public PedidoModel entityToDomain(PedidoEntity entity) {
        return new PedidoModel(
                entity.getNumeroPedido(),
                entity.getClienteId(),
                entity.getEstado(),
                entity.getFechaEntrega(),
                entity.getZonaId(),
                entity.isRequiereRefrigeracion()
        );
    }

    public PedidoModel dtoToDomain(PedidoDto dto) {
        return new PedidoModel(
                dto.getNumeroPedido(),
                dto.getClienteId(),
                dto.getEstado(),
                dto.getFechaEntrega(),
                dto.getZonaEntrega(),
                dto.isRequiereRefrigeracion()
        );
    }

    public PedidoDto domainToDto(PedidoModel pedido) {
        return new PedidoDto(
                pedido.getNumeroPedido(),
                pedido.getClienteId(),
                pedido.getFechaEntrega(),
                pedido.getEstado(),
                pedido.getZonaEntrega(),
                pedido.isRequiereRefrigeracion()
        );
    }

    public List<PedidoEntity> domainListToEntityList(List<PedidoModel> pedidoModels) {
        return pedidoModels.stream()
                .map(this::domainToEntity)
                .toList();
    }

}