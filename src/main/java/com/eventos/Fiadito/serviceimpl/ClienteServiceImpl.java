package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.ClienteDTO;
import com.eventos.Fiadito.dtos.ClienteRegistroDTO;
import com.eventos.Fiadito.models.*;
import com.eventos.Fiadito.repositories.*;
import com.eventos.Fiadito.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private AuthorityRepository authorityRepository;

    public ClienteDTO crearCliente(ClienteRegistroDTO clienteRegistroDTO) {
        // Buscar establecimiento
        Establecimiento establecimiento = establecimientoRepository.findById(clienteRegistroDTO.getCliente().getEstablecimientoId()).orElseThrow();
        // Verificar si existe usuario
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByUsername(clienteRegistroDTO.getUsuario().getUsername()));
        if (usuario.isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Verifica si existe cliente
        Cliente clienteExistente = clienteRepository.findByDni(clienteRegistroDTO.getCliente().getDni());
        if (clienteExistente != null) {
            throw new RuntimeException("El cliente ya existe");
        }
        // Verificar si correo existe cliente
        Cliente clienteExistenteEmail = clienteRepository.findByEmail(clienteRegistroDTO.getCliente().getEmail());
        if (clienteExistenteEmail != null) {
            throw new RuntimeException("El correo ya existe");
        }
        // Crear usuario
        Usuario nuevoUsuario = new Usuario(
                clienteRegistroDTO.getUsuario().getUsername(),
                new BCryptPasswordEncoder().encode(clienteRegistroDTO.getUsuario().getPassword()),
                clienteRegistroDTO.getUsuario().getEmail(),
                clienteRegistroDTO.getUsuario().getNombre(),
                List.of(authorityRepository.findByName(AuthorityName.ROLE_CLIENTE))
        );
        usuarioRepository.save(nuevoUsuario);
        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRegistroDTO.getCliente().getNombre());
        cliente.setDni(clienteRegistroDTO.getCliente().getDni());
        cliente.setDireccion(clienteRegistroDTO.getCliente().getDireccion());
        cliente.setEmail(clienteRegistroDTO.getCliente().getEmail());
        cliente.setTelefono(clienteRegistroDTO.getCliente().getTelefono());
        cliente.setFechaRegistro(new Date());
        cliente.setEstablecimiento(establecimiento);
        cliente.setUsuario(nuevoUsuario);
        clienteRepository.save(cliente);
        // Crear DTO
        return new ClienteDTO() {{
            setId(cliente.getId());
            setUsuarioId(nuevoUsuario.getId());
            setNombre(cliente.getNombre());
            setDni(cliente.getDni());
            setDireccion(cliente.getDireccion());
            setEmail(cliente.getEmail());
            setTelefono(cliente.getTelefono());
            setFechaRegistro(cliente.getFechaRegistro());
            setEstablecimientoId(cliente.getEstablecimiento().getId());
            setEnMora(cliente.isEnMora());
        }};
    }

    @Override
    public List<ClienteDTO> obtenerClientesPorEstablecimiento(Long establecimientoId) {
        List<Cliente> clientes = clienteRepository.findByEstablecimientoId(establecimientoId);
        return clientes.stream().map(cliente -> new ClienteDTO() {{
            setUsuarioId(getUsuarioId());
            setId(cliente.getId());
            setNombre(cliente.getNombre());
            setDni(cliente.getDni());
            setDireccion(cliente.getDireccion());
            setEmail(cliente.getEmail());
            setTelefono(cliente.getTelefono());
            setFechaRegistro(cliente.getFechaRegistro());
            setEstablecimientoId(cliente.getEstablecimiento().getId());
            if (cliente.getCuentaCorriente() != null) {
                setCuentaCorrienteId(cliente.getCuentaCorriente().getId());
            }
            else {
                setCuentaCorrienteId(null);
            }
            setEnMora(cliente.isEnMora());
        }}).collect(Collectors.toList());
    }

    //TODO: Actualizar datos cliente
    public ClienteDTO actualizarCliente(ClienteDTO clienteDTO) {
        // Buscar cuentaCorriente
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(clienteDTO.getCuentaCorrienteId()).orElseThrow();

        // Verificar si email aún está disponible
        Cliente clienteExistenteEmail = clienteRepository.findByEmail(clienteDTO.getEmail());
        if (clienteExistenteEmail != null && !clienteExistenteEmail.getId().equals(clienteDTO.getId())) {
            throw new RuntimeException("El correo ya existe");
        }

        Cliente cliente = clienteRepository.findById(clienteDTO.getId()).orElseThrow();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDni(clienteDTO.getDni());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEnMora(cliente.isEnMora());
        cliente.setCuentaCorriente(cuentaCorriente);
        cliente.setFechaRegistro(cliente.getFechaRegistro());
        cliente.setEstablecimiento(cliente.getEstablecimiento());
        cliente.setUsuario(cliente.getUsuario());
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
            setUsuarioId(cliente.getUsuario().getId());
            setId(cliente.getId());
            setNombre(cliente.getNombre());
            setDni(cliente.getDni());
            setDireccion(cliente.getDireccion());
            setEmail(cliente.getEmail());
            setTelefono(cliente.getTelefono());
            setFechaRegistro(cliente.getFechaRegistro());
            setEstablecimientoId(cliente.getEstablecimiento().getId());
            if (cliente.getCuentaCorriente() != null) {
                setCuentaCorrienteId(cliente.getCuentaCorriente().getId());
            }
            else {
                setCuentaCorrienteId(null);
            }
            setEnMora(cliente.isEnMora());
        }};
    }

    // Obtener cliente por usuario
    public ClienteDTO obtenerClientePorUsuario(Long usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }

        Cliente cliente = clienteRepository.findByUsuarioId(usuarioId);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado para el usuario con ID: " + usuarioId);
            // O bien, puedes devolver un valor predeterminado o un DTO vacío según tu lógica de negocio
            // return new ClienteDTO(); // Por ejemplo, devolver un DTO vacío
        }

        return new ClienteDTO() {{
            setUsuarioId(usuarioId);
            setId(cliente.getId());
            setNombre(cliente.getNombre());
            setDni(cliente.getDni());
            setDireccion(cliente.getDireccion());
            setEmail(cliente.getEmail());
            setTelefono(cliente.getTelefono());
            setFechaRegistro(cliente.getFechaRegistro());
            setEstablecimientoId(cliente.getEstablecimiento().getId());
            if (cliente.getCuentaCorriente() != null) {
                setCuentaCorrienteId(cliente.getCuentaCorriente().getId());
            }
            else {
                setCuentaCorrienteId(null);
            }
            setEnMora(cliente.isEnMora());
        }};
    }

}
