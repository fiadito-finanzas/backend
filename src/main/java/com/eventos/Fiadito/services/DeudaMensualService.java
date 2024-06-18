package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.DeudaMensualDTO;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.DeudaMensual;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface DeudaMensualService {
    public DeudaMensual obtenerDeudaMensualPorCuentaCorrienteEntreFechas(CuentaCorriente cuentaCorrienteId, Date fechaInicio, Date fechaFin);
}
