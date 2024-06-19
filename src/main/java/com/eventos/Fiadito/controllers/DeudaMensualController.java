package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.DeudaMensualDTO;
import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.services.DeudaMensualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/deuda-mensual")
public class DeudaMensualController {
    @Autowired
    private DeudaMensualService deudaMensualService;

    // Implementar encontrar deuda mensual por cuenta corriente entre fechas
    @GetMapping("/cuenta-corriente-entre-fechas")
    public ResponseEntity<?> obtenerDeudaMensualPorCuentaCorrienteEntreFechas(
            @RequestParam Long cuentaCorrienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        // Convertir LocalDate a Date
        Date fechaInicioDate = java.sql.Date.valueOf(fechaInicio);
        Date fechaFinDate = java.sql.Date.valueOf(fechaFin);
        return ResponseEntity.ok(deudaMensualService.obtenerDeudaMensualPorCuentaCorrienteEntreFechas(cuentaCorrienteId, fechaInicioDate, fechaFinDate));
    }


    @GetMapping("/cuenta-corriente-deudas")
    public ResponseEntity<?> obtenerDeudasMensualesPorCuentaCorriente(
            @RequestParam Long cuentaCorrienteId) {
        return ResponseEntity.ok(deudaMensualService.obtenerDeudasMensualesPorCuentaCorriente(cuentaCorrienteId));
    }

    @GetMapping("/cuenta-corriente-actual")
    public ResponseEntity<?> obtenerDeudaMensualActualPorCuentaCorriente(
            @RequestParam Long cuentaCorrienteId) {
        Optional<DeudaMensualDTO> deudaMensualActual = deudaMensualService.obtenerDeudaMensualActual(cuentaCorrienteId);

        if (deudaMensualActual.isPresent()) {
            return ResponseEntity.ok(deudaMensualActual.get());
        } else {
            return ResponseEntity.noContent().build();  // O puedes devolver un mensaje indicando que no se encontró la deuda mensual actual.
        }
    }

}
