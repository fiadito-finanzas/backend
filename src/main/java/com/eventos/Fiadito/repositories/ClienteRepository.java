package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    Cliente findByDni(String dni);
    Cliente findByEmail(String email);
    List<Cliente> findByEstablecimientoId(Long establecimientoId);
    Cliente findByUsuarioId(Long usuarioId);
}
