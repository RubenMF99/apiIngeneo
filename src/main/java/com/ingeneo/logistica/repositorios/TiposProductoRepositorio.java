package com.ingeneo.logistica.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.ingeneo.logistica.models.entity.Tiposproducto;

public interface TiposProductoRepositorio extends CrudRepository<Tiposproducto,Integer> {
    @Query("SELECT t FROM Tiposproducto t WHERE t.idCliente = :idCliente")
    List<Tiposproducto> findByClienteId(Integer idCliente);
}
