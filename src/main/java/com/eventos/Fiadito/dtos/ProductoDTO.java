package com.eventos.Fiadito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private double precio;
    private String descripcion;
}
