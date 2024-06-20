package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.repositories.DeudaMensualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface InteresMoratorioService {
    public void calcularInteresMoratorio();
}
