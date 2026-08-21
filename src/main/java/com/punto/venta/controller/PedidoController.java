package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
        private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoDTO> listarPedidos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public PedidoDTO obtenerPedidoPorId(@PathVariable Integer id) {
        return pedidoService.obtenerPedidoPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO guardarPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.guardarPedido(pedidoDTO);
    }

    @PutMapping("/{id}/anular")
    public PedidoDTO anularPedido(@PathVariable Integer id) {
        return pedidoService.anularPedido(id);
    }

    @DeleteMapping("/{id}")
    public MessegeResponse eliminarPedido(@PathVariable Integer id) {
        return pedidoService.eliminarPedido(id);
    }
}
