package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCuentaCorriente(CuentaCorriente cuentaCorriente);

}
