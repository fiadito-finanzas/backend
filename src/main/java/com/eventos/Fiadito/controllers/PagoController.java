package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.PagoDTO;
import com.eventos.Fiadito.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/registrar-pago")
    public ResponseEntity<?> registrarPago(@RequestBody PagoDTO pagoDTO) {
        return ResponseEntity.ok(pagoService.registrarPago(pagoDTO));
    }

    // Obtener pagos por cuenta corriente
    @GetMapping("/obtener-pagos-por-cuenta-corriente/{cuentaCorrienteId}")
    public ResponseEntity<?> obtenerPagosPorCuentaCorriente(@PathVariable Long cuentaCorrienteId){
        return ResponseEntity.ok(pagoService.obtenerPagosPorCuentaCorriente(cuentaCorrienteId));
    }

    // Obtener pagos por cliente
    @GetMapping("/obtener-pagos-por-cliente/{clienteId}")
    public ResponseEntity<?> obtenerPagosPorCliente(@PathVariable Long clienteId){
        return ResponseEntity.ok(pagoService.obtenerPagosPorCliente(clienteId));
    }
}
