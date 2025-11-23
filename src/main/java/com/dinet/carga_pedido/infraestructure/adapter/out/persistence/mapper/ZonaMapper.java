package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper;

import com.dinet.carga_pedido.domain.model.ZonaModel;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.ZonaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZonaMapper {

    public ZonaModel entityToDomain(ZonaEntity zonaEntity) {
        return new ZonaModel(
                zonaEntity.getId(),
                zonaEntity.isSoporteRefrigeracion()
        );
    }

    public List<ZonaModel> entitiesToDomains(List<ZonaEntity> zonaEntities) {
        List<ZonaModel> zonas = new ArrayList<>();
        for (ZonaEntity entity : zonaEntities) {
            zonas.add(entityToDomain(entity));
        }
        return zonas;
    }

}
