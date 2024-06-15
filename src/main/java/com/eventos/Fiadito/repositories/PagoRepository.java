package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{
}
