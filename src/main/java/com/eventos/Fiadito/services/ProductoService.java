package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.ProductoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductoService {

    // Listar productos
    List<ProductoDTO> listarProductos();

    // Crear producto
    ProductoDTO crearProducto(ProductoDTO productoDTO);

    // Actualizar producto
    ProductoDTO actualizarProducto(ProductoDTO productoDTO, Long productoId);

    // Eliminar producto
    void eliminarProducto(Long productoId);

}
