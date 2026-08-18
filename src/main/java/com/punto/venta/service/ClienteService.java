package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    private ClienteDTO convertirDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setEstado(cliente.getEstado());
        clienteDTO.setFechaRegistro(cliente.getFechaRegistro());
        return clienteDTO;
    }

    private Cliente convertirEntytyCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(clienteDTO.getIdCliente());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setEstado(clienteDTO.getEstado());
        cliente.setFechaRegistro(clienteDTO.getFechaRegistro());
        return cliente;
    }

    public ClienteDTO guardarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = convertirEntytyCliente(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return convertirDTO(clienteGuardado);
    }

    public ClienteDTO eliminarCliente(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente));
        cliente.setEstado(false);
        Cliente clienteEliminado = clienteRepository.save(cliente);
        return convertirDTO(clienteEliminado);
    }

    public ClienteDTO crearCliente(ClienteDTO clienteDTO){
        if (clienteRepository.existsByNombreIgnoreCaseAndApellidoIgnoreCase(clienteDTO.getNombre(), clienteDTO.getApellido())) {
            throw new RuntimeException("El cliente ya existe");
        }
        Cliente cliente = convertirEntytyCliente(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return convertirDTO(clienteGuardado);
    }
    
    public ClienteDTO modificarCliente(Integer idCliente, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente));
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEmail(clienteDTO.getEmail());
        Cliente clienteModificado = clienteRepository.save(cliente);
        return convertirDTO(clienteModificado);
    }

    public ClienteDTO modificarEstadoCliente(Integer idCliente, boolean estado) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente));
        cliente.setEstado(estado);
        Cliente clienteModificado = clienteRepository.save(cliente);
        return convertirDTO(clienteModificado);
    }
}
