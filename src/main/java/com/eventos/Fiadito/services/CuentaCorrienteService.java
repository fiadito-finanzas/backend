package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.Transaccion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuentaCorrienteService {
    List<Transaccion> obtenerTransacciones(Long cuentaCorrienteId);
}
