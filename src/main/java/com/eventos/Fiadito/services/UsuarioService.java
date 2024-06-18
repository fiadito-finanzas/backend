package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.UsuarioDTO;
import com.eventos.Fiadito.models.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO obtenerUsuario(Long usuarioId);
}
