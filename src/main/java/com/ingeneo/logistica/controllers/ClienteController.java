package com.ingeneo.logistica.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ingeneo.logistica.models.entity.Almacen;
import com.ingeneo.logistica.models.entity.Cliente;
import com.ingeneo.logistica.models.entity.Logistica;
import com.ingeneo.logistica.models.entity.Tiposproducto;
import com.ingeneo.logistica.repositorios.AlmacenRepositorio;
import com.ingeneo.logistica.repositorios.ClienteRepositorio;
import com.ingeneo.logistica.repositorios.LogisticaRepositorio;
import com.ingeneo.logistica.repositorios.TiposProductoRepositorio;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class ClienteController {
    @Autowired
	private ClienteRepositorio clienteRepositorio;
    @Autowired
    private TiposProductoRepositorio tiposRepositorio;
    @Autowired
    private LogisticaRepositorio logisticaRepositorio;
    @Autowired
    private AlmacenRepositorio almacenRepositorio;
    
    @PostMapping("/agregar")
    public Cliente agregarCliente(@RequestBody Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @PostMapping("/logistica/{idProduct}")
    public ResponseEntity<Object> postLogistica(@RequestBody Logistica logistica, @PathVariable Integer idProduct) {
        Optional<Tiposproducto> productoOptional = tiposRepositorio.findById(idProduct);
        if (productoOptional.isPresent()) {
            Tiposproducto tiposProducto = productoOptional.get();
            if (logistica.getCantidadproducto() > 10 && tiposProducto.getTransporte().equals("terrestre")) {
                logistica.setDescuento((logistica.getPrecioenvio() * 0.05));
                return ResponseEntity.ok(logisticaRepositorio.save(logistica));
            } else if(logistica.getCantidadproducto() > 10 && tiposProducto.getTransporte().equals("maritimo")){
                logistica.setDescuento((logistica.getPrecioenvio() * 0.03));
                return ResponseEntity.ok(logisticaRepositorio.save(logistica));
            }else{
                return ResponseEntity.ok(logistica);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró Tiposproducto con el identificador: " + idProduct);
        }
    }

    @PostMapping("/almacenaje")
    public ResponseEntity<Object> postAlmacen(@RequestBody Almacen almacen) {
        if(almacen != null){
            return ResponseEntity.ok(almacenRepositorio.save(almacen));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Datos no validos" );
    }

}
