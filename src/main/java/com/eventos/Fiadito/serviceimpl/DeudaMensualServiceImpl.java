package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.DeudaMensualDTO;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.repositories.DeudaMensualRepository;
import com.eventos.Fiadito.services.DeudaMensualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeudaMensualServiceImpl implements DeudaMensualService {
    @Autowired
    private DeudaMensualRepository deudaMensualRepository;

    public DeudaMensual obtenerDeudaMensualPorCuentaCorrienteEntreFechas(CuentaCorriente cuentaCorrienteId, Date fechaInicio, Date fechaFin) {
        DeudaMensual deudaEncontrada = deudaMensualRepository.findByCuentaCorrienteAndFechaInicioCicloAndFechaFinCiclo(cuentaCorrienteId, fechaInicio, fechaFin);
        if(deudaEncontrada != null){
            return deudaEncontrada;
        }else{
            return null;
        }
    }
}
