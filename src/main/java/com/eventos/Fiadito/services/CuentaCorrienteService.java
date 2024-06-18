package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.CuentaCorrienteDTO;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.Transaccion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuentaCorrienteService {
    CuentaCorrienteDTO crearCuentaCorriente(CuentaCorrienteDTO cuentaCorrienteDTO);
    CuentaCorrienteDTO obtenerCuentaCorriente(Long clienteId);
    //List<Transaccion> obtenerTransacciones(Long cuentaCorrienteId);
}
