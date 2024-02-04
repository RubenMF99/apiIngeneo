package com.ingeneo.logistica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ingeneo.logistica.controllers.ClienteController;
import com.ingeneo.logistica.models.entity.Cliente;
import com.ingeneo.logistica.repositorios.ClienteRepositorio;

public class ClienteTest {
    @Mock
      private ClienteRepositorio clienteRepositorio;
    @InjectMocks
      private  ClienteController clienteController;

    @BeforeEach
     public void setUp() {
        MockitoAnnotations.openMocks(this);  
    }
    @Test
    public void crearCliente() {
        // Arrange
        Cliente cliente = new Cliente(1, "Pedro", "Pedropass", "12345", "pedro@email.com");
        ResponseEntity<Object> response = clienteController.agregarCliente(cliente);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Cliente clienteGuardado = clienteRepositorio.findById(1).orElse(null);
        assertNotNull(clienteGuardado);
        assertEquals("Pedro", clienteGuardado.getNombre());
    }
    @Test
    public void testAgregarClienteConClienteNulo() {
        ResponseEntity<Object> response = clienteController.agregarCliente(null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Datos no validos", response.getBody());
    }

}
