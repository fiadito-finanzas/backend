package com.eventos.Fiadito.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_corriente_id", nullable = false)
    private CuentaCorriente cuentaCorriente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private String tipo; // Puede ser COMPRA, PAGO_DEUDA, o COMPRA_A_CUOTAS

    @Column(nullable = false)
    private double interes; // Interés aplicado a la transacción, si corresponde

    @Column(nullable = false)
    private double totalMonto; // Monto total de la transacción, incluyendo intereses, si corresponde
}
