package com.ingeneo.logistica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingeneo.logistica.models.entity.Tiposproducto;
import com.ingeneo.logistica.repositorios.TiposProductoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class ProductoController {
    @Autowired
    TiposProductoRepositorio tiposRepositorio;
    @PostMapping("agregar-producto")
    public Tiposproducto agregarProducto(@RequestBody Tiposproducto producto) {
        return  tiposRepositorio.save(producto);
    }
    
}
