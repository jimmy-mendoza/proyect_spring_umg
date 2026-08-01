package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    public List<CategoriaDTO>finAll(){
        return categoriaRepository.findAll()
        .stream()
        .map(this::convertirDTO)
        .collect(Collectors.toList());
    }

    private CategoriaDTO convertirDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        return categoriaDTO;
    }

public CategoriaDTO guardar(CategoriaDTO categoriaDTO){
        Categoria categoria = convertirEntidad(categoriaDTO);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirDTO(categoriaGuardada);
    }

private Categoria convertirEntidad(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(categoriaDTO.getIdCategoria());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setEstado(true);
        return categoria;
    }
}