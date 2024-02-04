package com.ingeneo.logistica.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.ingeneo.logistica.models.entity.Logistica;


public interface LogisticaRepositorio extends CrudRepository<Logistica,Integer>{
    @Query("SELECT l FROM logistica l WHERE l.idCliente = :idCliente")
    List<Logistica> findByClienteId(Integer idCliente);
}
