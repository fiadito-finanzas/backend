package com.eventos.Fiadito.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class CuentaCorriente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaCorriente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones;

    @OneToMany(mappedBy = "cuentaCorriente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeudaMensual> deudasMensuales;

    @Column(nullable = false)
    private Double tasaInteres;

    @Column(nullable = false)
    private Double tasaMoratoria;

    @Column(nullable = false)
    private Double saldoCredito;

    @Column(nullable = false)
    private Date fechaPagoMensual;

    @Column(nullable = false)
    private String tipoInteres; //Nominal - Efectivo

    @Column(nullable = false)
    private String periodoCapitalizacion; //Diario - Mensual - Quincenal - Semestral

    @Column(nullable = false)
    private Double m; // Numero de periodos de capitalizacion que hay en 30 días | mensual

    @Column(nullable = false)
    private Double n; // Numero de periodos de capitalización que hay en 30 dias

    // TEP = ( 1 + TN / m ) ^ n - 1
    @Column(nullable = false)
    private Double TEP;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaUltimaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = fechaUltimaActualizacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaUltimaActualizacion = new Date();
    }

}
