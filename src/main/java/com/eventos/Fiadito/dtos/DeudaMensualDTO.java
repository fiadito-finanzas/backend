package com.eventos.Fiadito.dtos;

import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.Transaccion;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeudaMensualDTO {
    private Long transaccionId;
    private Long cuentaCorrienteId;
    private Date mes;
    private Date fechaInicioCiclo; // Fecha de inicio del ciclo de pago
    private Date fechaFinCiclo; // Fecha de fin del ciclo de pago
    private Date fechaPago;
    private double monto;
    private double interes;
    private boolean pagada;
}
