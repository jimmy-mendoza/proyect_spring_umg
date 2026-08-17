package com.punto.venta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClienteDTO {
    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private Boolean estado;
    private Date fechaRegistro;
}
