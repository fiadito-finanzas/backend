package com.eventos.Fiadito.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ClienteDTO {
    private Long usuarioId;
    private String nombre;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;
    private Date fechaRegistro;
    private Long establecimientoId;
    private boolean enMora;
    private Long cuentaCorrienteId;
}
