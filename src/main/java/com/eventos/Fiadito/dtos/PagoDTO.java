package com.eventos.Fiadito.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PagoDTO {
        private double monto;
        private String metodoPago;
        private Long clienteId;
        private Long deudaMensualId;
        private boolean isCuota;
        private Long cuotaId;
        private Date fechaPago;
}
