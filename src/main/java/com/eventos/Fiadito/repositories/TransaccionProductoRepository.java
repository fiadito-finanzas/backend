package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.TransaccionProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionProductoRepository  extends JpaRepository<TransaccionProducto, Long> {
}
