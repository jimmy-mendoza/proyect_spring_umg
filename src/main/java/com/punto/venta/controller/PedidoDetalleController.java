package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.service.PedidoDetalleService;

@RestController
@RequestMapping("/pedido-detalles")
@CrossOrigin(origins = "*")
public class PedidoDetalleController {
    private final PedidoDetalleService pedidoDetalleService;

    public PedidoDetalleController(PedidoDetalleService pedidoDetalleService) {
        this.pedidoDetalleService = pedidoDetalleService;
    }

    @GetMapping
    public List<PedidoDetalleDTO> listarTodos() {
        return pedidoDetalleService.listarTodos();
    }

    @GetMapping("/{id}")
    public PedidoDetalleDTO obtenerPorId(@PathVariable Integer id) {
        return pedidoDetalleService.obtenerPorId(id);
    }

    @GetMapping("/pedido/{idPedido}")
    public List<PedidoDetalleDTO> listarPorPedido(@PathVariable Integer idPedido) {
        return pedidoDetalleService.listarPorPedido(idPedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDetalleDTO guardarDetalle(@RequestBody PedidoDetalleDTO dto) {
        return pedidoDetalleService.guardarDetalle(dto);
    }

    @DeleteMapping("/{id}")
    public MessegeResponse eliminarDetalle(@PathVariable Integer id) {
        return pedidoDetalleService.eliminarDetalle(id);
    }
}