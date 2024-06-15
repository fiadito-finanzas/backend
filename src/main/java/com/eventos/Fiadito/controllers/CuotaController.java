package com.eventos.Fiadito.controllers;

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
    public List<Cuota> obtenerCuotasPorTransaccion(@PathVariable Long transaccionId){
        return cuotaService.obtenerCuotasPorTransaccion(transaccionId);
    }

    // TODO: Obtener cuota
    @GetMapping("/obtener-cuota/{cuotaId}")
    public Cuota obtenerCuota(@PathVariable Long cuotaId){
        return cuotaService.obtenerCuota(cuotaId);
    }

}