package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.Cuota;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuotaService {

    //Cuota registrarCuota(Cuota cuota);
    List<Cuota> obtenerCuotasPorCliente(Long clienteId);

    //TODO: Obtener cuotas por transaccion
    List<Cuota> obtenerCuotasPorTransaccion(Long transaccionId);

    //TODO: Obtener cuota
    Cuota obtenerCuota(Long cuotaId);

}
