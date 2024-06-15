package com.eventos.Fiadito.dtos;

import com.eventos.Fiadito.models.Cliente;
import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.models.Transaccion;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CuentaCorrienteDTO {
    private Long id;
    private Long clienteId;
    private List<TransaccionDTO> transacciones;
    private List<DeudaMensualDTO> deudasMensuales;
    private double tasaInteres;
    private double tasaMoratoria;
    private double saldoCredito;
    private Date fechaPagoMensual;
    private String tipoInteres; //Nominal - Efectivo
    private String periodoCapitalizacion; //Diario - Mensual - Quincenal - Semestral
}
