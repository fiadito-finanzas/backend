package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    @Query("SELECT c FROM Cuota c WHERE c.transaccion.id = ?1")
    List<Cuota> findByTransaccionId(Long transaccionId);
}
