package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.ClienteDTO;
import com.eventos.Fiadito.models.Cliente;
import com.eventos.Fiadito.models.ConfiguracionCredito;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {
    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    //Cliente findById(Long id);
    //List<Cliente> listarClientes();
}
