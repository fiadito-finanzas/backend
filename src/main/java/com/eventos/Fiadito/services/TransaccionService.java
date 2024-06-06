package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.Transaccion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransaccionService {
    List<Transaccion> registrarTransaccion(Transaccion transaccion);

    List<Transaccion> obtenerTransaccionesPorCuentaCorriente(String cuentaCorriente);

    Transaccion obtenerTransaccionPorId(Long transaccionId);
}
