package com.punto.venta.dto;

import java.math.BigDecimal;

import com.punto.venta.entity.Categoria;

import lombok.Data;

@Data
public class ProductoDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Boolean estado;
    private Integer idCategoria;
}
