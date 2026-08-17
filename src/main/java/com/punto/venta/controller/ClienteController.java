package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.entity.Cliente;
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
}
