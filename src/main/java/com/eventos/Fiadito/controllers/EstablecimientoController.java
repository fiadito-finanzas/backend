package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.EstablecimientoDTO;
import com.eventos.Fiadito.dtos.EstablecimientoRegistroDTO;
import com.eventos.Fiadito.repositories.ClienteRepository;
import com.eventos.Fiadito.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/establecimiento")
public class EstablecimientoController {
    @Autowired
    private EstablecimientoService establecimientoService;


    @PostMapping("/registrar-establecimiento")
    public EstablecimientoDTO registrarEstablecimiento(@RequestBody EstablecimientoRegistroDTO establecimientoDTO) {
        return establecimientoService.registrarEstablecimiento(establecimientoDTO);
    }

    // Obtener establecimiento
    @GetMapping("/obtener-establecimiento/{establecimientoId}")
    public EstablecimientoDTO obtenerEstablecimiento(@PathVariable Long establecimientoId) {
        return establecimientoService.obtenerEstablecimiento(establecimientoId);
    }

    // Obtener establecimiento por userId
    @GetMapping("/obtener-establecimiento-por-usuario/{usuarioId}")
    public EstablecimientoDTO obtenerEstablecimientoPorUsuario(@PathVariable Long usuarioId) {
        return establecimientoService.obtenerEstablecimientoPorUsuario(usuarioId);
    }

    // Actualizar establecimiento
    @PutMapping("/actualizar-establecimiento")
    public EstablecimientoDTO actualizarEstablecimiento(@RequestBody EstablecimientoDTO establecimientoDTO) {
        return establecimientoService.actualizarEstablecimiento(establecimientoDTO);
    }

    // Eliminar establecimiento
    @DeleteMapping("/eliminar-establecimiento/{establecimientoId}")
    public ResponseEntity<?> eliminarEstablecimiento(@PathVariable Long establecimientoId) {
        return ResponseEntity.ok(establecimientoService.eliminarEstablecimiento(establecimientoId));
    }
}
