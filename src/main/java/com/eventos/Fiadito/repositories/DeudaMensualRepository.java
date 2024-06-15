package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.DeudaMensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeudaMensualRepository extends JpaRepository<DeudaMensual, Long>{
    DeudaMensual findByCuentaCorrienteAndFechaInicioCicloAndFechaFinCiclo(CuentaCorriente cuentaCorriente, Date fechaInicioCiclo, Date fechaFinCiclo);

}
