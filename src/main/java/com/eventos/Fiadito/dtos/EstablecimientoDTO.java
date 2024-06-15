package com.eventos.Fiadito.dtos;

import lombok.Data;

@Data
public class EstablecimientoDTO {
    private Long id;
    private String nombre;
    private Long usuarioId;
    private String direccion;
    private String rubro;
    private String ciudad;
    private String provincia;
}
