package com.ingeneo.logistica.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.ingeneo.logistica.models.entity.Cliente;

public interface ClienteRepositorio extends CrudRepository<Cliente, Integer>{

}
