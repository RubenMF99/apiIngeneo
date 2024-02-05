package com.ingeneo.logistica.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ingeneo.logistica.models.entity.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    Cliente findByEmailAndPassword(String email, String password);
    Cliente findByEmail(String email);
}
