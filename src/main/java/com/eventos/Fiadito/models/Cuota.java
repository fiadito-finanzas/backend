package com.eventos.Fiadito.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
