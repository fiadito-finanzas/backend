package com.eventos.Fiadito.controllers;


import com.eventos.Fiadito.dtos.ProductoDTO;
import com.eventos.Fiadito.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping("/listar")
    public List<ProductoDTO> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping("/crear")
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @PutMapping("/actualizar/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productoService.actualizarProducto(productoDTO, id);
    }

    @DeleteMapping("/eliminar/{productoId}")
    public void eliminarProducto(@PathVariable Long productoId) {
        productoService.eliminarProducto(productoId);
    }


}
