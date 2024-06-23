package com.eventos.Fiadito.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaccion_id", nullable = false)
    private Transaccion transaccion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaVencimiento;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private boolean pagada;

    @Column(nullable = false)
    private double montoInteres;

    @Column(nullable = false)
    private double montoAmortizacion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaInicioCiclo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaFinCiclo;

    @Column(nullable = false)
    private String periodoGracia; // 'T' - 'P' - 'S'

    @Column(nullable = false)
    private double saldoFlujo;

    @Column(nullable = false)
    private double numeroCuota;

    @Column(nullable = false)
    private double saldoInicial;

    @Column(nullable = false)
    private double montoCapital;
}
