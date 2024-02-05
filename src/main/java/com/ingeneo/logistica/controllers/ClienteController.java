package com.ingeneo.logistica.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.ingeneo.logistica.models.entity.Cliente;
import com.ingeneo.logistica.models.entity.Logistica;
import com.ingeneo.logistica.models.entity.Tiposproducto;
import com.ingeneo.logistica.repositorios.ClienteRepositorio;
import com.ingeneo.logistica.repositorios.LogisticaRepositorio;
import com.ingeneo.logistica.repositorios.TiposProductoRepositorio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class ClienteController {
    private final ClienteRepositorio clienteRepositorio;
    @Autowired
    private TiposProductoRepositorio tiposRepositorio;
    @Autowired
    private LogisticaRepositorio logisticaRepositorio;

    // Constructor que inyecta el repositorio
    public ClienteController(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }
    
    @PostMapping("/agregar")
    public ResponseEntity<Object> agregarCliente(@RequestBody Cliente cliente) {
        if(cliente != null){
            return ResponseEntity.ok(clienteRepositorio.save(cliente));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Datos no validos" );
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody  Map<String, Object> loginRequest) {
        String email = (String) loginRequest.get("email");
        String password = (String) loginRequest.get("password");

        Cliente cliente = clienteRepositorio.findByEmailAndPassword(email, password);
        if (cliente != null) {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            String token = Jwts.builder()
                    .setSubject(cliente.getEmail())
                    .signWith(key)
                    .compact();
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
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
    @GetMapping("/logistica")
    public ResponseEntity<Object> getListLogisticaByIdCliente(@RequestParam Integer idCliente) {
        try {
            List<Logistica> guiaEnvio = logisticaRepositorio.findByClienteId(idCliente);
            return ResponseEntity.ok(guiaEnvio);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }
}
