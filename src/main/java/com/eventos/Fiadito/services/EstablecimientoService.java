package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.EstablecimientoDTO;
import com.eventos.Fiadito.models.Establecimiento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstablecimientoService {

    EstablecimientoDTO registrarEstablecimiento(EstablecimientoDTO establecimiento);
    //Establecimiento obtenerEstablecimientoPorId(Long establecimientoId);
}
