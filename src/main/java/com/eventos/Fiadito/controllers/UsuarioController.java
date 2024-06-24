package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.AuthResponseDTO;
import com.eventos.Fiadito.dtos.UserAuthDTO;
import com.eventos.Fiadito.dtos.UsuarioDTO;
import com.eventos.Fiadito.jwt.JwtService;
import com.eventos.Fiadito.services.AuthenticationService;
import com.eventos.Fiadito.services.UserDetailsService;
import com.eventos.Fiadito.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear-usuario")
    public UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private UsuarioService userService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody UserAuthDTO userAuthDTO) {
        if (userAuthDTO.getUsername() == null || userAuthDTO.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authenticationService.login(userAuthDTO), HttpStatus.OK);
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthDTO userAuthDTO) {
        try {
            return ResponseEntity.ok(authenticationService.login(userAuthDTO));
        } catch (AuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //Obtener usuario
    @GetMapping("/obtener-usuario/{usuarioId}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(usuarioId));
    }

    //JWT Verify
    @PostMapping("/jwt/verify")
    public ResponseEntity<?> verifyToken(@RequestBody String token) {
        return ResponseEntity.ok(jwtService.verifyToken(token));
    }

}
