package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.CuentaCorrienteDTO;
import com.eventos.Fiadito.services.CuentaCorrienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cuenta-corriente")
public class CuentaCorrienteController {
    @Autowired
    private CuentaCorrienteService cuentaCorrienteService;

    @PostMapping("/crear-cuenta-corriente")
    public ResponseEntity<?> creaCuentaCorriente(@RequestBody CuentaCorrienteDTO cuentaCorrienteDTO) {
        return ResponseEntity.ok(cuentaCorrienteService.crearCuentaCorriente(cuentaCorrienteDTO));
    }

    @GetMapping("/obtener-cuenta-corriente/{clienteId}")
    public ResponseEntity<?> obtenerCuentaCorriente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(cuentaCorrienteService.obtenerCuentaCorriente(clienteId));
    }

    @PutMapping("/actualizar-cuenta-corriente")
    public ResponseEntity<?> actualizarCuentaCorriente(@RequestBody CuentaCorrienteDTO cuentaCorrienteDTO) {
        return ResponseEntity.ok(cuentaCorrienteService.actualizarCuentaCorriente(cuentaCorrienteDTO));
    }

    @DeleteMapping("/eliminar-cuenta-corriente/{cuentaCorrienteId}")
    public ResponseEntity<?> eliminarCuentaCorriente(@PathVariable Long cuentaCorrienteId) {
        return ResponseEntity.ok(cuentaCorrienteService.eliminarCuentaCorriente(cuentaCorrienteId));
    }

}
