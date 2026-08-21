package com.punto.venta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punto.venta.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByEstadoTrue();
    
    List<Producto> findByIdCategoria(Integer IdCategoria);

    List<Producto> findBystockLessThan(Integer stock);
}
