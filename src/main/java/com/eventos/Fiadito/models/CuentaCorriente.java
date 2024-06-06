package com.eventos.Fiadito.models;


import jakarta.persistence.*;
import lombok.Data;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "configuracion_credito_id", referencedColumnName = "id")
    private ConfiguracionCredito configuracionCredito;

    @Column(nullable = false)
    private double saldo;
}
