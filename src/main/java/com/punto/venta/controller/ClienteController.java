package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.repository.ClienteRepository;
import com.punto.venta.service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteService clienteService, ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<ClienteDTO> listarClientes() {
        return clienteService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<MessegeResponse> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.crearCliente(clienteDTO);
            return ResponseEntity.ok(new MessegeResponse("Cliente creado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new MessegeResponse("Error al crear el cliente: " + e.getMessage()));    
        }
}   
    @PutMapping("/{id}")
    public ResponseEntity<MessegeResponse> modificarCliente(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.modificarCliente(id, clienteDTO);
            return ResponseEntity.ok(new MessegeResponse("Cliente modificado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessegeResponse("Error al modificar el cliente: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessegeResponse> eliminarCliente(@PathVariable Integer id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.ok(new MessegeResponse("Cliente eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessegeResponse("Error al eliminar el cliente: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<MessegeResponse> modificarEstadoCliente(@PathVariable Integer id, @RequestParam boolean estado) {
        try {
            clienteService.modificarEstadoCliente(id, estado);
            return ResponseEntity.ok(new MessegeResponse("Estado del cliente actualizado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessegeResponse("Error al actualizar estado: " + e.getMessage()));
        }
    }
}
