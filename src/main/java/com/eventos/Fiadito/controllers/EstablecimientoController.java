package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.EstablecimientoDTO;
import com.eventos.Fiadito.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/establecimiento")
public class EstablecimientoController {
    @Autowired
    private EstablecimientoService establecimientoService;

    @PostMapping("/registrar-establecimiento")
    public EstablecimientoDTO registrarEstablecimiento(@RequestBody EstablecimientoDTO establecimientoDTO) {
        return establecimientoService.registrarEstablecimiento(establecimientoDTO);
    }
}
