package com.punto.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.service.CategoriaService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
@Autowired
private CategoriaService categoriaService;
public CategoriaController(CategoriaService categoriaService) {
    this.categoriaService = categoriaService;
}
@GetMapping
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaService.finAll();
    }

    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.guardar(categoriaDTO);
    }

    @DeleteMapping("/{id}")
    public MessegeResponse eliminarCategoria(@PathVariable Integer id) {
    return categoriaService.eliminarCategoria(id);
    }

        @PutMapping("/{id}/anular") 
        public CategoriaDTO anularCategoria1(@PathVariable Integer id) {
            return categoriaService.anularCategoria(id);
}
}

