package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.Cliente;
import com.eventos.Fiadito.models.ConfiguracionCredito;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {
    Cliente crearCliente(String username, String direccion, String telefono, ConfiguracionCredito configuracionCredito);
    Cliente findById(Long id);
    List<Cliente> listarClientes();
}
