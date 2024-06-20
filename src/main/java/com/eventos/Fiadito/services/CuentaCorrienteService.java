package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.CuentaCorrienteDTO;
import org.springframework.stereotype.Service;

@Service
public interface CuentaCorrienteService {
    CuentaCorrienteDTO crearCuentaCorriente(CuentaCorrienteDTO cuentaCorrienteDTO);
    CuentaCorrienteDTO obtenerCuentaCorriente(Long clienteId);
    CuentaCorrienteDTO actualizarCuentaCorriente(CuentaCorrienteDTO cuentaCorrienteDTO);
    boolean eliminarCuentaCorriente(Long cuentaCorrienteId);
    //List<Transaccion> obtenerTransacciones(Long cuentaCorrienteId);
}
