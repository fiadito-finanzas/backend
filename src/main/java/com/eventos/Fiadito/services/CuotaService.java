package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.Cuota;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuotaService {

    Cuota registrarCuota(Cuota cuota);
    List<Cuota> obtenerCuotasPorTransaccion(Long transaccionId);
    List<Cuota> obtenerCuotasPorCliente(Long clienteId);

}
