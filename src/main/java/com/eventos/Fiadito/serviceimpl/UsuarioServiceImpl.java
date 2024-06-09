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
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setRole(usuarioDTO.getRole());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNombre(usuarioDTO.getNombre());

        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        usuarioDTO.setId(nuevoUsuario.getId());
        return usuarioDTO;
    }
}
