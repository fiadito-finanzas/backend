package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.CuotaDTO;
import com.eventos.Fiadito.dtos.FlujoCuotaDTO;
import com.eventos.Fiadito.models.Cuota;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuotaService {

    //Cuota registrarCuota(Cuota cuota);
    List<Cuota> obtenerCuotasPorCliente(Long clienteId);

    //TODO: Obtener cuotas por transaccion
    List<Cuota> obtenerCuotasPorTransaccion(Long transaccionId);

    //TODO: Obtener flujos cuotas
    List<FlujoCuotaDTO> obtenerFlujosCuotas(Long transaccionId);

    //TODO: Obtener cuota
    Cuota obtenerCuota(Long cuotaId);

    //TODO: Obtener cuotas por deuda mensual
    List<CuotaDTO> obtenerCuotasPorDeudaMensual(Long deudaMensualId);


}
