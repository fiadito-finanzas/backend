package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.CuotaDTO;
import com.eventos.Fiadito.dtos.FlujoCuotaDTO;
import com.eventos.Fiadito.models.Cuota;
import com.eventos.Fiadito.services.CuotaService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cuota")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;


    // TODO: obtenerCuotasPorTransaccion
    @GetMapping("/obtener-cuotas-por-transaccion/{transaccionId}")
    public List<FlujoCuotaDTO> obtenerCuotasPorTransaccion(@PathVariable Long transaccionId){
        return cuotaService.obtenerFlujosCuotas(transaccionId);
    }

    // TODO: obtenerCuotasPorDeudaMensual
    @GetMapping("/obtener-cuotas-por-deuda-mensual/{deudaMensualId}")
    public List<CuotaDTO> obtenerCuotasPorDeudaMensual(@PathVariable Long deudaMensualId){
        return cuotaService.obtenerCuotasPorDeudaMensual(deudaMensualId);
    }

    // TODO: Obtener cuota
    @GetMapping("/obtener-cuota/{cuotaId}")
    public Cuota obtenerCuota(@PathVariable Long cuotaId){
        return cuotaService.obtenerCuota(cuotaId);
    }

}
