package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.ClienteDTO;
import com.eventos.Fiadito.dtos.ClienteRegistroDTO;
import com.mysql.cj.xdevapi.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {
    ClienteDTO crearCliente(ClienteRegistroDTO clienteRegistroDTO);
    //Cliente findById(Long id);
    //List<Cliente> listarClientes();
    ClienteDTO obtenerClientePorUsuario(Long usuarioId);

    //TODO: Obtener clientes por establecimiento
    List<ClienteDTO> obtenerClientesPorEstablecimiento(Long establecimientoId);

    //TODO: Actualizar datos cliente
    ClienteDTO actualizarCliente(ClienteDTO clienteDTO);

    //TODO: Eliminar Cliente
    boolean eliminarCliente(Long clienteId);

    //TODO: Obtener Cliente
    ClienteDTO obtenerCliente(Long clienteId);
}
