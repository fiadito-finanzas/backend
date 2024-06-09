package com.eventos.Fiadito.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String email;
    private String nombre;
}
