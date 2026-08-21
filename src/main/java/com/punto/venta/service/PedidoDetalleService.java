package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.PedidoDetalleRepository;
import com.punto.venta.repository.PedidoRepository;
import com.punto.venta.repository.ProductoRepository;

@Service
public class PedidoDetalleService {
    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoDetalleService(PedidoDetalleRepository pedidoDetalleRepository,
        PedidoRepository pedidoRepository,
        ProductoRepository productoRepository) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<PedidoDetalleDTO> listarTodos() {
        return pedidoDetalleRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDetalleDTO> listarPorPedido(Integer idPedido) {
        return pedidoDetalleRepository.findByIdPedido_IdPedido(idPedido)
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public PedidoDetalleDTO obtenerPorId(Integer idPedidoDetalle) {
        PedidoDetalle detalle = pedidoDetalleRepository.findById(idPedidoDetalle)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado con ID: " + idPedidoDetalle));
        return convertirDTO(detalle);
    }

    public PedidoDetalleDTO guardarDetalle(PedidoDetalleDTO dto) {
        PedidoDetalle detalle = convertirEntidad(dto);
        detalle.setIdPedidoDetalle(null); // fuerza siempre un INSERT
        PedidoDetalle detalleGuardado = pedidoDetalleRepository.save(detalle);
        return convertirDTO(detalleGuardado);
    }

    public MessegeResponse eliminarDetalle(Integer idPedidoDetalle) {
        if (!pedidoDetalleRepository.existsById(idPedidoDetalle)) {
            throw new RuntimeException("No se puede eliminar el detalle con ID " + idPedidoDetalle + " porque no existe.");
        }
        pedidoDetalleRepository.deleteById(idPedidoDetalle);
        return new MessegeResponse("Detalle con ID " + idPedidoDetalle + " eliminado correctamente.");
    }

    public PedidoDetalleDTO convertirDTO(PedidoDetalle detalle) {
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setIdPedidoDetalle(detalle.getIdPedidoDetalle());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setIdPedido(detalle.getIdPedido() != null ? detalle.getIdPedido().getIdPedido() : null);
        dto.setIdProducto(detalle.getIdProducto() != null ? detalle.getIdProducto().getIdProducto() : null);
        return dto;
    }

    public PedidoDetalle convertirEntidad(PedidoDetalleDTO dto) {
        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setIdPedidoDetalle(dto.getIdPedidoDetalle());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());

        if (dto.getIdPedido() != null) {
            Pedido pedido = pedidoRepository.findById(dto.getIdPedido())
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + dto.getIdPedido()));
            detalle.setIdPedido(pedido);
        }

        if (dto.getIdProducto() != null) {
            Producto producto = productoRepository.findById(dto.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getIdProducto()));
            detalle.setIdProducto(producto);
        }

        return detalle;
    }
}