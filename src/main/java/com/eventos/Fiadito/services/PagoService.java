package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.PagoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PagoService {
    PagoDTO registrarPago(PagoDTO pagoDTO);

    //TODO: Obtener pagos por cuenta corriente
    List<PagoDTO> obtenerPagosPorCuentaCorriente(Long cuentaCorrienteId);

    //TODO: Obtener pagos por cliente
    List<PagoDTO> obtenerPagosPorCliente(Long clienteId);


}
