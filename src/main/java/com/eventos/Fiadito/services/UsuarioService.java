package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    Usuario crearUsuario(String username, String password);
    Usuario findByUsername(String username);
}
