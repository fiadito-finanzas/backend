package com.eventos.Fiadito.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cliente> clientes;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String rubro;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String provincia;
}
