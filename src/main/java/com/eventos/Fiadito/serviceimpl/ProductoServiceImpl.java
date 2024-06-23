package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.ProductoDTO;
import com.eventos.Fiadito.models.Producto;
import com.eventos.Fiadito.repositories.ProductoRepository;
import com.eventos.Fiadito.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();

        // Crear DTO
        List<ProductoDTO> productosDTO = productos.stream().map(producto -> new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion()
        )).toList();
        return productosDTO;
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setDescripcion(productoDTO.getDescripcion());

        Producto productoGuardado = productoRepository.save(producto);

        ProductoDTO nuevoProductoDTO = new ProductoDTO(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getPrecio(),
                productoGuardado.getDescripcion()
        );

        return nuevoProductoDTO;

    }

    @Override
    public ProductoDTO actualizarProducto(ProductoDTO productoDTO, Long productoId) {
        Producto producto = productoRepository.findById(productoId).get();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setDescripcion(productoDTO.getDescripcion());

        Producto productoGuardado = productoRepository.save(producto);

        ProductoDTO nuevoProductoDTO = new ProductoDTO(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getPrecio(),
                productoGuardado.getDescripcion()
        );

        return nuevoProductoDTO;
    }

    @Override
    public void eliminarProducto(Long productoId) {
        productoRepository.deleteById(productoId);
    }


}
