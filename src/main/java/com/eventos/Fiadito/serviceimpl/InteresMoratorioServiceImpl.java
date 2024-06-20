package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.repositories.CuentaCorrienteRepository;
import com.eventos.Fiadito.repositories.DeudaMensualRepository;
import com.eventos.Fiadito.services.InteresMoratorioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InteresMoratorioServiceImpl implements InteresMoratorioService {
    @Autowired
    private DeudaMensualRepository deudaMensualRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // Se ejecuta todos los días a la medianoche
    public void calcularInteresMoratorio() {
        calcularInteresMoratorio(LocalDate.now());
    }

    public void calcularInteresMoratorio(LocalDate fechaActual) {
        List<DeudaMensual> deudasMensuales = deudaMensualRepository.findByPagadoFalse();
        // Formulas del calculo de intereses moratorios
        // Im = ValorNominal * [(1+TEPm)^(n dias trasladar/n dias TEPm)-1]
        // Im = ValorNominal * [(1+TNAm/m)^n-1]
        for (DeudaMensual deudaMensual : deudasMensuales) {
            LocalDate fechaVencimiento = LocalDate.parse(deudaMensual.getFechaFinCiclo().toLocaleString());
            long diasAtraso = fechaVencimiento.until(fechaActual).getDays();
            CuentaCorriente cuentaCorriente = deudaMensual.getCuentaCorriente();

            if (cuentaCorriente.getTipoInteres().equals("Efectiva")) {
                double TEPm = cuentaCorriente.getTEP();
                double Im = deudaMensual.getMonto() * (Math.pow(1 + TEPm, diasAtraso / 30.0) - 1);
                double montoTotal = deudaMensual.getMonto() + Im;
                deudaMensual.setMonto(montoTotal);
            } else if (cuentaCorriente.getTipoInteres().equals("Nominal")) {
                double TNAm = cuentaCorriente.getTasaInteres() * 0.01;
                double m = cuentaCorriente.getM();
                double n = cuentaCorriente.getN();
                double Im = deudaMensual.getMonto() * (Math.pow(1 + TNAm / m, n) - 1);
                double montoTotal = deudaMensual.getMonto() + Im;
                deudaMensual.setMonto(montoTotal);
            }

            deudaMensualRepository.save(deudaMensual);
        }
    }

}
