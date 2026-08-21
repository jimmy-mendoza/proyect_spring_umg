package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.MessegeResponse;
import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;
import com.punto.venta.repository.ClienteRepository;
import com.punto.venta.repository.PedidoRepository;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO obtenerPedidoPorId(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + idPedido));
        return convertirDTO(pedido);
    }

    public PedidoDTO guardarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = convertirEntidadPedido(pedidoDTO);
        pedido.setIdPedido(null); // fuerza siempre un INSERT
        pedido.setEstado(true);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return convertirDTO(pedidoGuardado);
    }

    public PedidoDTO anularPedido(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + idPedido));
        pedido.setEstado(false);
        Pedido pedidoAnulado = pedidoRepository.save(pedido);
        return convertirDTO(pedidoAnulado);
    }

    public MessegeResponse eliminarPedido(Integer idPedido) {
        if (!pedidoRepository.existsById(idPedido)) {
            throw new RuntimeException("No se puede eliminar el pedido con ID " + idPedido + " porque no existe.");
        }
        pedidoRepository.deleteById(idPedido);
        return new MessegeResponse("Pedido con ID " + idPedido + " eliminado correctamente.");
    }

    public PedidoDTO convertirDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setIdPedido(pedido.getIdPedido());
        pedidoDTO.setEstado(pedido.getEstado());
        pedidoDTO.setFechaPedido(pedido.getFechaPedido());
        pedidoDTO.setEstadoPedido(pedido.getEstadoPedido());
        pedidoDTO.setTotal(pedido.getTotal());
        pedidoDTO.setIdCliente(pedido.getIdCliente() != null ? pedido.getIdCliente().getIdCliente() : null);
        return pedidoDTO;
    }

    public Pedido convertirEntidadPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(pedidoDTO.getIdPedido());
        pedido.setFechaPedido(pedidoDTO.getFechaPedido());
        pedido.setEstadoPedido(pedidoDTO.getEstadoPedido());
        pedido.setTotal(pedidoDTO.getTotal());

        if (pedidoDTO.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(pedidoDTO.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + pedidoDTO.getIdCliente()));
            pedido.setIdCliente(cliente);
        }

        return pedido;
    }
}
