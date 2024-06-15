package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCuentaCorriente(CuentaCorriente cuentaCorriente);


    @Query("SELECT t FROM Transaccion t WHERE t.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Transaccion> findByFechaBetween(Date fechaInicio, Date fechaFin);
}
