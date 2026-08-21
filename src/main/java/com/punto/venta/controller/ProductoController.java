package com.punto.venta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.repository.ProductoRepository;
import com.punto.venta.service.ProductoService;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoService productoService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Integer id) {
        return productoService.obtenerProductoPorId(id);    
    }

    @PostMapping
    public ProductoDTO guardarProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.guardarProducto(productoDTO);
    }

    @PutMapping("/anular/{id}")
    public ProductoDTO anularProducto(@PathVariable Integer id) {
    return productoService.anularProducto(id);
    }
    @DeleteMapping("/{id}")
    public MessegeResponse eliminarProducto(@PathVariable Integer id) {
    return productoService.eliminarProducto(id);
    }
    
    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Integer id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoExistente = productoService.obtenerProductoPorId(id);
        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        return productoService.guardarProducto(productoExistente);      
}
}