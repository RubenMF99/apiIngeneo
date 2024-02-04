package com.ingeneo.logistica.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ingeneo.logistica.models.entity.Logistica;


public interface LogisticaRepositorio extends CrudRepository<Logistica, Integer> {
    @Query("SELECT l FROM Logistica l WHERE l.idCliente = :idCliente")
    List<Logistica> findByClienteId(@Param("idCliente") Integer idCliente);
}

