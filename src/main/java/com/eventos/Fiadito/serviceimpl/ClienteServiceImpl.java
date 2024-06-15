package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.ClienteDTO;
import com.eventos.Fiadito.models.Cliente;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.Establecimiento;
import com.eventos.Fiadito.models.Usuario;
import com.eventos.Fiadito.repositories.ClienteRepository;
import com.eventos.Fiadito.repositories.CuentaCorrienteRepository;
import com.eventos.Fiadito.repositories.EstablecimientoRepository;
import com.eventos.Fiadito.repositories.UsuarioRepository;
import com.eventos.Fiadito.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        Establecimiento establecimiento = establecimientoRepository.findById(clienteDTO.getEstablecimientoId()).orElseThrow();
        // Validar que no exista el cliente
        Cliente clienteExistente = clienteRepository.findByDni(clienteDTO.getDni());

        // Que el usuario no esté siendo usado
        if (clienteExistente != null) {
            throw new RuntimeException("Cliente ya existe");
        }

        // Buscar usuario
        Optional<Usuario> usuario = usuarioRepository.findById(clienteDTO.getUsuarioId());

        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

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
        cliente.setUsuario(usuario.get());
        cliente.setCuentaCorriente(null);
        if (clienteExistente != null) {
            throw new RuntimeException("Cliente ya existe");
        }

        Cliente nuevoCliente = clienteRepository.save(cliente);
        clienteDTO.setUsuarioId(nuevoCliente.getId());
        return clienteDTO;
    }

    @Override
    public List<ClienteDTO> obtenerClientesPorEstablecimiento(Long establecimientoId) {
        List<Cliente> clientes = clienteRepository.findByEstablecimientoId(establecimientoId);
        return clientes.stream().map(cliente -> new ClienteDTO() {{
            setUsuarioId(cliente.getId());
            setNombre(cliente.getNombre());
            setDni(cliente.getDni());
            setDireccion(cliente.getDireccion());
            setEmail(cliente.getEmail());
            setTelefono(cliente.getTelefono());
            setFechaRegistro(cliente.getFechaRegistro());
            setEstablecimientoId(cliente.getEstablecimiento().getId());
            setCuentaCorrienteId(cliente.getCuentaCorriente().getId());
            setEnMora(cliente.isEnMora());
        }}).collect(Collectors.toList());
    }

    //TODO: Actualizar datos cliente
    public ClienteDTO actualizarCliente(ClienteDTO clienteDTO) {
        // Buscar cuentaCorriente
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(clienteDTO.getCuentaCorrienteId()).orElseThrow();

        Cliente cliente = clienteRepository.findById(clienteDTO.getUsuarioId()).orElseThrow();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDni(clienteDTO.getDni());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEnMora(clienteDTO.isEnMora());
        cliente.setCuentaCorriente(cuentaCorriente);
        clienteRepository.save(cliente);
        return clienteDTO;
    }

    // Eliminar cliente
    public boolean eliminarCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        clienteRepository.delete(cliente);
        return true;
    }

    // Obtener cliente
    public ClienteDTO obtenerCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        return new ClienteDTO() {{
            setUsuarioId(cliente.getId());
            setNombre(cliente.getNombre());
            setDni(cliente.getDni());
            setDireccion(cliente.getDireccion());
            setEmail(cliente.getEmail());
            setTelefono(cliente.getTelefono());
            setFechaRegistro(cliente.getFechaRegistro());
            setEstablecimientoId(cliente.getEstablecimiento().getId());
            setCuentaCorrienteId(cliente.getCuentaCorriente().getId());
            setEnMora(cliente.isEnMora());
        }};
    }

}
