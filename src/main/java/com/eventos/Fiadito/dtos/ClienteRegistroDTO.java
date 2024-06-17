package com.eventos.Fiadito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteRegistroDTO {
    private UsuarioDTO usuario;
    private ClienteDTO cliente;
}
