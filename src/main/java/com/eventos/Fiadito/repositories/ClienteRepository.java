package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    Cliente findByDni(String dni);
}
