package com.ingeneo.logistica.repositorios;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ingeneo.logistica.models.entity.Cliente;

public interface ClienteRepositorio extends CrudRepository<Cliente, Integer> {
    @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.password = :password")
    Cliente findByCorreoAndContrasena(@Param("email") String email, @Param("password") String password);
}
