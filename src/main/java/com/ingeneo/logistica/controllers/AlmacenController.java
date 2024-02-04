package com.ingeneo.logistica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ingeneo.logistica.models.entity.Almacen;
import com.ingeneo.logistica.repositorios.AlmacenRepositorio;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api")
public class AlmacenController {

    @Autowired
    private AlmacenRepositorio almacenRepositorio;

    @PostMapping("/almacenaje")
    public ResponseEntity<Object> postAlmacen(@RequestBody Almacen almacen) {
        if(almacen != null){
            return ResponseEntity.ok(almacenRepositorio.save(almacen));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Datos no validos" );
    }
}
