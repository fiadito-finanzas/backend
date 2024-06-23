package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.DeudaMensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeudaMensualRepository extends JpaRepository<DeudaMensual, Long>{
    DeudaMensual findByCuentaCorrienteAndFechaInicioCicloAndFechaFinCiclo(CuentaCorriente cuentaCorriente, Date fechaInicioCiclo, Date fechaFinCiclo);
    List<DeudaMensual> findByCuentaCorriente(CuentaCorriente cuentaCorriente);

    @Query("SELECT d FROM DeudaMensual d WHERE d.cuentaCorriente.id = :cuentaCorrienteId AND " +
            "MONTH(d.fechaFinCiclo) = MONTH(:currentDate) AND " +
            "YEAR(d.fechaFinCiclo) = YEAR(:currentDate)")
    Optional<DeudaMensual> findDeudaMensualActual(@Param("cuentaCorrienteId") Long cuentaCorrienteId,
                                                  @Param("currentDate") Date currentDate);

    @Query("SELECT d FROM DeudaMensual d WHERE d.pagada = false")
    List<DeudaMensual> findByPagadoFalse();
}
