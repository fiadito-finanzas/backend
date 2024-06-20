package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.DeudaMensualDTO;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.repositories.CuentaCorrienteRepository;
import com.eventos.Fiadito.repositories.DeudaMensualRepository;
import com.eventos.Fiadito.services.DeudaMensualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeudaMensualServiceImpl implements DeudaMensualService {
    @Autowired
    private DeudaMensualRepository deudaMensualRepository;

    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;

    public DeudaMensual obtenerDeudaMensualPorCuentaCorrienteEntreFechas(Long cuentaCorrienteId, Date fechaInicio, Date fechaFin) {
        // Buscar Cuenta Corriente
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(cuentaCorrienteId).orElse(null);

        DeudaMensual deudaEncontrada = deudaMensualRepository.findByCuentaCorrienteAndFechaInicioCicloAndFechaFinCiclo(cuentaCorriente, fechaInicio, fechaFin);
        if(deudaEncontrada != null){
            return deudaEncontrada;
        }else{
            return null;
        }
    }

    public List<DeudaMensualDTO> obtenerDeudasMensualesPorCuentaCorriente(Long cuentaCorrienteId) {
        // Buscar Cuenta Corriente
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(cuentaCorrienteId).orElse(null);

        // Buscar deudas mensuales
        List<DeudaMensual> deudasMensuales = deudaMensualRepository.findByCuentaCorriente(cuentaCorriente);
        List<DeudaMensualDTO> deudasMensualesDTO = new ArrayList<>();
        for (DeudaMensual deudaMensual : deudasMensuales) {
            DeudaMensualDTO deudaMensualDTO = new DeudaMensualDTO();
            deudaMensualDTO.setCuentaCorrienteId(deudaMensual.getCuentaCorriente().getId());
            deudaMensualDTO.setFechaInicioCiclo(deudaMensual.getFechaInicioCiclo());
            deudaMensualDTO.setFechaFinCiclo(deudaMensual.getFechaFinCiclo());
            deudaMensualDTO.setFechaPago(deudaMensual.getFechaFinCiclo());
            deudaMensualDTO.setMonto(deudaMensual.getMonto());
            deudasMensualesDTO.add(deudaMensualDTO);
        }
        return deudasMensualesDTO;
    }

    @Override
    public Optional<DeudaMensualDTO> obtenerDeudaMensualActual(Long cuentaCorrienteId) {
        Date currentDate = new Date();
        Optional<DeudaMensual> deudaMensual = deudaMensualRepository.findDeudaMensualActual(cuentaCorrienteId, currentDate);
        if (deudaMensual.isPresent()) {
            DeudaMensualDTO deudaMensualDTO = new DeudaMensualDTO();
            deudaMensualDTO.setCuentaCorrienteId(deudaMensual.get().getCuentaCorriente().getId());
            deudaMensualDTO.setFechaInicioCiclo(deudaMensual.get().getFechaInicioCiclo());
            deudaMensualDTO.setFechaFinCiclo(deudaMensual.get().getFechaFinCiclo());
            deudaMensualDTO.setFechaPago(deudaMensual.get().getFechaFinCiclo());
            deudaMensualDTO.setMonto(deudaMensual.get().getMonto());
            deudaMensualDTO.setInteres(deudaMensual.get().getInteres());
            deudaMensualDTO.setId(deudaMensual.get().getId());
            return Optional.of(deudaMensualDTO);
        } else {
            return Optional.empty();
        }
    }
}
