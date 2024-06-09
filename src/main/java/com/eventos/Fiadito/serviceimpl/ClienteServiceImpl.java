package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.ClienteDTO;
import com.eventos.Fiadito.models.Cliente;
import com.eventos.Fiadito.models.Establecimiento;
import com.eventos.Fiadito.repositories.ClienteRepository;
import com.eventos.Fiadito.repositories.EstablecimientoRepository;
import com.eventos.Fiadito.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        Establecimiento establecimiento = establecimientoRepository.findById(clienteDTO.getEstablecimientoId()).orElseThrow();
        if (establecimiento == null) {
            throw new RuntimeException("Establecimiento no encontrado");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDni(clienteDTO.getDni());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setFechaRegistro(new Date());
        cliente.setEstablecimiento(establecimiento);
        cliente.setEnMora(clienteDTO.isEnMora());
        cliente.setCuentaCorriente(null);

        Cliente nuevoCliente = clienteRepository.save(cliente);
        clienteDTO.setId(nuevoCliente.getId());
        return clienteDTO;
    }
}
