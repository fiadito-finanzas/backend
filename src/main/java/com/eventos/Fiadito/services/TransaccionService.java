package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.TransaccionDTO;
import com.eventos.Fiadito.models.Transaccion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransaccionService {
    TransaccionDTO registrarTransaccion(TransaccionDTO transaccionDTO);

    List<TransaccionDTO> obtenerTransaccionesPorCuentaCorriente(Long cuentaCorriente);

    //TODO: Obtener transaccion por ID
    Transaccion obtenerTransaccionPorId(Long transaccionId);

    //TODO: Obtener transacciones por establecimiento
    List<Transaccion> obtenerTransaccionesPorEstablecimiento(Long establecimientoId);

    //TODO: Obtener transacciones entre fechas
    List<TransaccionDTO> obtenerTransaccionesEntreFechas(String fechaInicio, String fechaFin);

}
