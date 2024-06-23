package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.models.Producto;
import com.eventos.Fiadito.models.Transaccion;
import com.eventos.Fiadito.models.TransaccionProducto;
import com.eventos.Fiadito.repositories.ProductoRepository;
import com.eventos.Fiadito.repositories.TransaccionProductoRepository;
import com.eventos.Fiadito.repositories.TransaccionRepository;
import com.eventos.Fiadito.services.TransaccionProductoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaccionProductoServiceImpl implements TransaccionProductoService{

    @Autowired
    private TransaccionProductoRepository transaccionProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;


    @Transactional
    public TransaccionProducto crearTransaccionProducto(Long transaccionId, Long ProductoId, Integer cantidad){
        Transaccion transaccion = transaccionRepository.findById(transaccionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaccion no encontrada"));

        Producto producto = productoRepository.findById(ProductoId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        TransaccionProducto transaccionProducto = new TransaccionProducto();
        transaccionProducto.setTransaccion(transaccion);
        transaccionProducto.setProducto(producto);
        transaccionProducto.setCantidad(cantidad);

        transaccionProductoRepository.save(transaccionProducto);
        return transaccionProducto;
    }
}
