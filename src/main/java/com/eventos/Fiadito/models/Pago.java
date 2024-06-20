package com.eventos.Fiadito.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private Date fechaPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deuda_mensual_id", nullable = false)
    private DeudaMensual deudaMensual;

}
