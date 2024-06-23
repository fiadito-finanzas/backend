package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.TransaccionDTO;
import com.eventos.Fiadito.services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/transaccion")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping("/registrar-transaccion")
    public ResponseEntity<?> registrarTransaccion(@RequestBody TransaccionDTO transaccionDTO) {
        return ResponseEntity.ok(transaccionService.registrarTransaccion(transaccionDTO));
    }

    //Obtener transacciones por cuenta corriente
    @GetMapping("/obtener-transacciones-por-cuenta-corriente/{cuentaCorrienteId}")
    public ResponseEntity<?> obtenerTransaccionesPorCuentaCorriente(@PathVariable Long cuentaCorrienteId) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesPorCuentaCorriente(cuentaCorrienteId));
    }

    //Obtener transacciones por establecimiento
    @GetMapping("/obtener-transacciones-por-establecimiento/{establecimientoId}")
    public ResponseEntity<?> obtenerTransaccionesPorEstablecimiento(@PathVariable Long establecimientoId) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesPorEstablecimiento(establecimientoId));
    }

    //Obtener transacciones
    @GetMapping("/obtener-transacciones")
    public ResponseEntity<?> obtenerTransacciones() {
        return ResponseEntity.ok(transaccionService.obtenerTransacciones());
    }


    //Obtener transaccion
    @GetMapping("/obtener-transaccion/{transaccionId}")
    public ResponseEntity<?> obtenerTransaccion(@PathVariable Long transaccionId) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionPorId(transaccionId));
    }

    //Obtener transacciones entre fechas
    @GetMapping("/obtener-transacciones-entre-fechas/{fechaInicio}/{fechaFin}")
    public ResponseEntity<?> obtenerTransaccionesEntreFechas(@PathVariable String fechaInicio, @PathVariable String fechaFin) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesEntreFechas(fechaInicio, fechaFin));
    }

}
