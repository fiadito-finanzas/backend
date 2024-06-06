package com.eventos.Fiadito.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class ConfiguracionCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double tasaInteres;

    @Column(nullable = false)
    private double tasaMoratoria;

    @Column(nullable = false)
    private double limiteCredito;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaPagoMensual;
}
