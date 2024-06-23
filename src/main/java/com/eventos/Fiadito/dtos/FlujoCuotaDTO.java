package com.eventos.Fiadito.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class FlujoCuotaDTO {
    private Long id;
    private Long numeroCuota;
    private double saldoInicial;
    private double montoCapital;
    private double montoInteres;
    private double montoAmortizacion;
    private double saldoFlujo;
    private boolean pagada;
    private String periodoGracia;
    private double monto;
    private Date fechaVencimiento;
}
