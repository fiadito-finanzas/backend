package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.EstablecimientoDTO;
import com.eventos.Fiadito.models.Establecimiento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstablecimientoService {

    EstablecimientoDTO registrarEstablecimiento(EstablecimientoDTO establecimiento);
    //Establecimiento obtenerEstablecimientoPorId(Long establecimientoId);

    //TODO: Actualizar datos establecimiento
    EstablecimientoDTO actualizarEstablecimiento(EstablecimientoDTO establecimiento);

    //TODO: Eliminar establecimiento
    boolean eliminarEstablecimiento(Long establecimientoId);

    //TODO: Obtener establecimiento
    EstablecimientoDTO obtenerEstablecimiento(Long establecimientoId);
}
