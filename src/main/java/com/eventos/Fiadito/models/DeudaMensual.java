package com.eventos.Fiadito.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class DeudaMensual {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_corriente_id", nullable = false)
    private CuentaCorriente cuentaCorriente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaccion_id", nullable = false)
    private Transaccion transaccion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaTransaccion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio_ciclo", nullable = false)
    private Date fechaInicioCiclo; // Fecha de inicio del ciclo de pago

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin_ciclo", nullable = false)
    private Date fechaFinCiclo; // Fecha de fin del ciclo de pago

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private double interes;

    @Column(nullable = false)
    private boolean pagada;

    @OneToMany(mappedBy = "deudaMensual", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos = new ArrayList<>();

}
