package com.dinet.carga_pedido.infraestructure.adapter.out.persistence.repository.jpa;

import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.ZonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ZonaJpaRepository extends JpaRepository<ZonaEntity, String> {

    boolean existsById(String id);

    @Query("select z.soporteRefrigeracion from ZonaEntity z where z.id = :idZona")
    boolean findSoporteRefrigeracionById(@Param("idZona") String id);

    List<ZonaEntity> findByIdIn(Set<String> ids);

}
