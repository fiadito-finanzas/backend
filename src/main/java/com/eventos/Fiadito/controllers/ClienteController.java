package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.dtos.ClienteDTO;
import com.eventos.Fiadito.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/obtener-clientes-por-establecimiento/{establecimientoId}")
    public ResponseEntity<?> obtenerClientesPorEstablecimiento(@PathVariable Long establecimientoId) {
        List<ClienteDTO> clientes = clienteService.obtenerClientesPorEstablecimiento(establecimientoId);
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/actualizar-cliente")
    public ClienteDTO actualizarCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = clienteService.actualizarCliente(clienteDTO);
        return clienteActualizado;
    }

    @DeleteMapping("/eliminar-cliente/{clienteId}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(clienteService.eliminarCliente(clienteId));
    }

    @GetMapping("/obtener-cliente/{clienteId}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(clienteService.obtenerCliente(clienteId));
    }
}
