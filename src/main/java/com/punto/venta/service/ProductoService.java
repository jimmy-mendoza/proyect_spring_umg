package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.CategoriaRepository;
import com.punto.venta.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO obtenerProductoPorId(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
        return convertirDTO(producto);
    }
    
    
public ProductoDTO anularProducto(Integer idProducto) {
    Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
    producto.setEstado(false);
    Producto productoAnulado = productoRepository.save(producto);
    return convertirDTO(productoAnulado);
}


public MessegeResponse eliminarProducto(Integer idProducto) {
    if (!productoRepository.existsById(idProducto)) {
        throw new RuntimeException("No se puede eliminar el producto con ID " + idProducto + " porque no existe.");
    }
    productoRepository.deleteById(idProducto);
    return new MessegeResponse("Producto con ID " + idProducto + " eliminado correctamente.");
}

    public ProductoDTO guardarProducto(ProductoDTO productoDTO) {
        Producto producto = convertirEntidadProducto(productoDTO);
        producto.setIdProducto(null); // fuerza siempre un INSERT
        producto.setEstado(true);
        Producto productoGuardado = productoRepository.save(producto);
        return convertirDTO(productoGuardado);
    }


    public ProductoDTO convertirDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setIdProducto(producto.getIdProducto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        productoDTO.setEstado(producto.getEstado());
        productoDTO.setIdCategoria(producto.getIdCategoria() != null ? producto.getIdCategoria().getIdCategoria() : null);
        return productoDTO;
    }

public Producto convertirEntidadProducto(ProductoDTO productoDTO) {
    Producto producto = new Producto();
    producto.setIdProducto(productoDTO.getIdProducto());
    producto.setNombre(productoDTO.getNombre());
    producto.setDescripcion(productoDTO.getDescripcion());
    producto.setPrecio(productoDTO.getPrecio());
    producto.setStock(productoDTO.getStock());
    producto.setEstado(productoDTO.getEstado());

    if (productoDTO.getIdCategoria() != null) {
    Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + productoDTO.getIdCategoria()));
    producto.setIdCategoria(categoria);
}
    return producto;
}

}


