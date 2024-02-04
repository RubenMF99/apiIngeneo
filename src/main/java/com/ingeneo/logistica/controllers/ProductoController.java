package com.ingeneo.logistica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingeneo.logistica.models.entity.Tiposproducto;
import com.ingeneo.logistica.repositorios.TiposProductoRepositorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class ProductoController {
    @Autowired
    TiposProductoRepositorio tiposRepositorio;
    @PostMapping("/producto")
    public Tiposproducto agregarProducto(@RequestBody Tiposproducto producto) {
        return  tiposRepositorio.save(producto);
    }
    @GetMapping("/producto")
    public ResponseEntity<Object> getListProducts(@RequestParam Integer idCliente) {
        try{
            List<Tiposproducto> tiposProductos = tiposRepositorio.findByClienteId(idCliente);
            return ResponseEntity.ok(tiposProductos);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        } 
    }
}
