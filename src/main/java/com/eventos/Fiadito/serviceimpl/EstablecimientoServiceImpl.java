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

        Establecimiento nuevoEstablecimiento = establecimientoRepository.save(establecimiento);
        establecimientoDTO.setId(nuevoEstablecimiento.getId());
        return establecimientoDTO;
    }
}
