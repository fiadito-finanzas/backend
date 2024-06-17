package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.EstablecimientoDTO;
import com.eventos.Fiadito.dtos.EstablecimientoRegistroDTO;
import com.eventos.Fiadito.models.AuthorityName;
import com.eventos.Fiadito.models.Establecimiento;
import com.eventos.Fiadito.models.Usuario;
import com.eventos.Fiadito.repositories.AuthorityRepository;
import com.eventos.Fiadito.repositories.EstablecimientoRepository;
import com.eventos.Fiadito.repositories.UsuarioRepository;
import com.eventos.Fiadito.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoServiceImpl implements EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public EstablecimientoDTO registrarEstablecimiento(EstablecimientoRegistroDTO establecimientoDTO) {

        // Verificar si el usuario ya existe
        Usuario usuario = usuarioRepository.findByUsername(establecimientoDTO.getUsuario().getUsername());
        // Si el usuario no existe crear usuario
        if (usuario != null) {
            throw new RuntimeException("Usuario existente");
        }
        // Crear usuario
        Usuario nuevoUsuario = new Usuario(
                establecimientoDTO.getUsuario().getUsername(),
                new BCryptPasswordEncoder().encode(establecimientoDTO.getUsuario().getPassword()),
                establecimientoDTO.getUsuario().getEmail(),
                establecimientoDTO.getUsuario().getNombre(),
                List.of(authorityRepository.findByName(AuthorityName.ROLE_ESTABLECIMIENTO))
        );
        usuarioRepository.save(nuevoUsuario);

        // Crear establecimiento
        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setNombre(establecimientoDTO.getEstablecimiento().getNombre());
        establecimiento.setDireccion(establecimientoDTO.getEstablecimiento().getDireccion());
        establecimiento.setRubro(establecimientoDTO.getEstablecimiento().getRubro());
        establecimiento.setProvincia(establecimientoDTO.getEstablecimiento().getProvincia());
        establecimiento.setCiudad(establecimientoDTO.getEstablecimiento().getCiudad());
        establecimiento.setUsuario(nuevoUsuario);
        Establecimiento nuevoEstablecimiento = establecimientoRepository.save(establecimiento);

        // Crear establecimientoDTO
        EstablecimientoDTO nuevoEstablecimientoDTO = new EstablecimientoDTO();
        nuevoEstablecimientoDTO.setId(nuevoEstablecimiento.getId());
        nuevoEstablecimientoDTO.setNombre(nuevoEstablecimiento.getNombre());
        nuevoEstablecimientoDTO.setDireccion(nuevoEstablecimiento.getDireccion());
        nuevoEstablecimientoDTO.setRubro(nuevoEstablecimiento.getRubro());
        nuevoEstablecimientoDTO.setProvincia(nuevoEstablecimiento.getProvincia());
        nuevoEstablecimientoDTO.setCiudad(nuevoEstablecimiento.getCiudad());
        nuevoEstablecimientoDTO.setUsuarioId(nuevoEstablecimiento.getUsuario().getId());
        return nuevoEstablecimientoDTO;

    }

    //TODO: Actualizar datos establecimiento
    public EstablecimientoDTO actualizarEstablecimiento(EstablecimientoDTO establecimientoDTO) {
        if (!establecimientoRepository.existsById(establecimientoDTO.getId())) {
            throw new RuntimeException("Establecimiento no encontrado");
        }
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoDTO.getId()).get();
        establecimiento.setNombre(establecimientoDTO.getNombre());
        establecimiento.setDireccion(establecimientoDTO.getDireccion());
        establecimiento.setRubro(establecimientoDTO.getRubro());
        establecimiento.setProvincia(establecimientoDTO.getProvincia());
        establecimiento.setCiudad(establecimientoDTO.getCiudad());
        establecimientoRepository.save(establecimiento);
        return establecimientoDTO;
    }

    //TODO: Eliminar establecimiento
    public boolean eliminarEstablecimiento(Long establecimientoId) {
        if (!establecimientoRepository.existsById(establecimientoId)) {
            throw new RuntimeException("Establecimiento no encontrado");
        }
        establecimientoRepository.deleteById(establecimientoId);
        return true;
    }

    //TODO: Obtener establecimiento
    public EstablecimientoDTO obtenerEstablecimiento(Long establecimientoId) {
        if (!establecimientoRepository.existsById(establecimientoId)) {
            throw new RuntimeException("Establecimiento no encontrado");
        }
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId).get();
        EstablecimientoDTO establecimientoDTO = new EstablecimientoDTO();
        establecimientoDTO.setId(establecimiento.getId());
        establecimientoDTO.setNombre(establecimiento.getNombre());
        establecimientoDTO.setDireccion(establecimiento.getDireccion());
        establecimientoDTO.setRubro(establecimiento.getRubro());
        establecimientoDTO.setProvincia(establecimiento.getProvincia());
        establecimientoDTO.setCiudad(establecimiento.getCiudad());
        return establecimientoDTO;
    }


}
