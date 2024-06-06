package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.Establecimiento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstablecimientoService {

    Establecimiento registrarEstablecimiento(Establecimiento establecimiento);
    Establecimiento obtenerEstablecimientoPorId(Long establecimientoId);
    List<Establecimiento> listarEstablecimientos();

}
