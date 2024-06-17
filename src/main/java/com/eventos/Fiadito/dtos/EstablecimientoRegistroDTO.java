package com.eventos.Fiadito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstablecimientoRegistroDTO {
    private UsuarioDTO usuario;
    private EstablecimientoDTO establecimiento;
}
