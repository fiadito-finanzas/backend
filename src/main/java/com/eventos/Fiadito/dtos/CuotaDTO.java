package com.eventos.Fiadito.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CuotaDTO {
    private Long id;
    private Long transaccionId;
    private Date fechaVencimiento;
    private double monto;
    private boolean pagada;
}
