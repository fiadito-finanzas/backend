package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.TransaccionProducto;
import org.springframework.stereotype.Service;

@Service
public interface TransaccionProductoService {
    TransaccionProducto crearTransaccionProducto(Long transaccionid, Long productoid, Integer cantidad);
}
