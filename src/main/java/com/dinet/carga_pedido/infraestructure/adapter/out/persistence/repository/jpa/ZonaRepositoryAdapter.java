package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.domain.model.ZonaModel;
import com.dinet.carga_pedido.domain.port.out.ZonaRepository;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.ZonaEntity;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper.ZonaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ZonaRepositoryAdapter implements ZonaRepository {

    private final ZonaJpaRepository zonaJpaRepository;
    private final ZonaMapper zonaMapper;

    public ZonaRepositoryAdapter(ZonaJpaRepository zonaJpaRepository, ZonaMapper zonaMapper) {
        this.zonaJpaRepository = zonaJpaRepository;
        this.zonaMapper = zonaMapper;
    }

    @Override
    public boolean existsById(String idZona) {
        return zonaJpaRepository.existsById(idZona);
    }

    @Override
    public boolean getSoporteRefrigeracion(String idZona) {
        return zonaJpaRepository.findSoporteRefrigeracionById(idZona);
    }

    @Override
    public List<ZonaModel> findByIdIn(Set<String> ids) {
        List<ZonaEntity> zonaEntities = zonaJpaRepository.findByIdIn(ids);
        return zonaMapper.entitiesToDomains(zonaEntities);
    }

}
