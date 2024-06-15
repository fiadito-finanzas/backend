package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.CuentaCorriente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, Long> {
    CuentaCorriente findByClienteId(Long cliente);

}
