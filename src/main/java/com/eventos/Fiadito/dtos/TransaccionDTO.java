package com.eventos.Fiadito.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TransaccionDTO {
    private Long id;
    private Long cuentaCorrienteId;
    private Date fecha;
    private double monto;
    private String tipo; // Puede ser COMPRA o COMPRA_A_CUOTAS
    private double interes;
    private int cuotas;
    private List<ProductoCompraDTO> productos; // Lista de IDs de productos asociados a la transacción
    private int cantidadPlazoGraciaT;
    private int cantidadPlazoGraciaP;
}
