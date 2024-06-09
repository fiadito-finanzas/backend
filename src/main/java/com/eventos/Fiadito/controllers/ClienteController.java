package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.ClienteDTO;
import com.eventos.Fiadito.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear-cliente")
    public ClienteDTO crearCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.crearCliente(clienteDTO);
        return nuevoCliente;
    }
}
