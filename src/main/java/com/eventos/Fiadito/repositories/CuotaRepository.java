package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.Cuota;
import com.eventos.Fiadito.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    List<Cuota> findByTransaccion(Transaccion transaccion);
}
