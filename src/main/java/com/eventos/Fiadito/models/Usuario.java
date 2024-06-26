package com.eventos.Fiadito.models;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_authorities",
            joinColumns = {
                    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false, updatable = false)
            }
    )
    private List<Authority> authorities;

    public Usuario() {
    }

    public Usuario(String username, String password, String email, String nombre, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
        this.authorities = authorities;
    }

}
