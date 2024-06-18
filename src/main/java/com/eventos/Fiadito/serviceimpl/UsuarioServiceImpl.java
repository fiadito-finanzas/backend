package com.eventos.Fiadito.serviceimpl;


import com.eventos.Fiadito.dtos.UsuarioDTO;
import com.eventos.Fiadito.models.Usuario;
import com.eventos.Fiadito.repositories.UsuarioRepository;
import com.eventos.Fiadito.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        return null;
    }

    public UsuarioDTO obtenerUsuario(Long usuarioId) {
        // Verificar si existe usuario
        Usuario usuario = usuarioRepository.findById(usuarioId).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Construir DTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setNombre(usuario.getNombre());
        return usuarioDTO;
    }
}
