package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.EstablecimientoDTO;
import com.eventos.Fiadito.models.Establecimiento;
import com.eventos.Fiadito.models.Usuario;
import com.eventos.Fiadito.repositories.EstablecimientoRepository;
import com.eventos.Fiadito.repositories.UsuarioRepository;
import com.eventos.Fiadito.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstablecimientoServiceImpl implements EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EstablecimientoDTO registrarEstablecimiento(EstablecimientoDTO establecimientoDTO) {
        if (!usuarioRepository.existsById(establecimientoDTO.getUsuarioId())) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = usuarioRepository.findById(establecimientoDTO.getUsuarioId()).get();

        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setNombre(establecimientoDTO.getNombre());
        establecimiento.setUsuario(usuario);
        establecimiento.setDireccion(establecimientoDTO.getDireccion());
        establecimiento.setRubro(establecimientoDTO.getRubro());
        establecimiento.setProvincia(establecimientoDTO.getProvincia());
        establecimiento.setCiudad(establecimientoDTO.getCiudad());

        Establecimiento nuevoEstablecimiento = establecimientoRepository.save(establecimiento);
        establecimientoDTO.setId(nuevoEstablecimiento.getId());
        return establecimientoDTO;
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
