package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.DeudaMensualDTO;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.DeudaMensual;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface DeudaMensualService {
    public DeudaMensual obtenerDeudaMensualPorCuentaCorrienteEntreFechas(Long cuentaCorrienteId, Date fechaInicio, Date fechaFin);
    public List<DeudaMensualDTO> obtenerDeudasMensualesPorCuentaCorriente(Long cuentaCorrienteId);
    public Optional<DeudaMensualDTO> obtenerDeudaMensualActual(Long cuentaCorrienteId);

}
