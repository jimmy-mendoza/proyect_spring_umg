package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.dto.MessegeResponse;
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

public CategoriaDTO guardar(CategoriaDTO categoriaDTO){
        Categoria categoria = convertirEntidad(categoriaDTO);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirDTO(categoriaGuardada);
    }

    public CategoriaDTO anularCategoria(Integer idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setEstado(false);
        categoria.setEstado(categoriaDTO.getEstado());

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertirDTO(savedCategoria);
    }

public CategoriaDTO modificarCategoria(Integer idCategoria, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));

        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertirDTO(savedCategoria);
    }

    public MessegeResponse eliminarCategoria(Integer idCategoria){
    if(!categoriaRepository.existsById(idCategoria)){
        throw new RuntimeException("No se puede eliminar la categoría con ID " + idCategoria + " porque no existe.");       
    }

    categoriaRepository.deleteById(idCategoria);
    return new MessegeResponse("Categoría con ID " + idCategoria + " eliminada correctamente.");
}

private Categoria convertirEntidad(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(categoriaDTO.getIdCategoria());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setEstado(true);
        return categoria;
    }

    private CategoriaDTO convertirDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        categoriaDTO.setEstado(categoria.getEstado());
        return categoriaDTO;
    }

}