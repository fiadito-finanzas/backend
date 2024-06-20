package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.CuotaDTO;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.Cuota;
import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.models.Transaccion;
import com.eventos.Fiadito.repositories.*;
import com.eventos.Fiadito.services.CuotaService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuotaServiceImpl implements CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private DeudaMensualRepository deudaMensualRepository;
    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;


    // TODO: obtener cuotas por transaccion
    @Override
    public List<Cuota> obtenerCuotasPorTransaccion(Long transaccionId){
        return cuotaRepository.findByTransaccionId(transaccionId);
    }

    // TODO: obtener cuota
    @Override
    public Cuota obtenerCuota(Long cuotaId){
        return cuotaRepository.findById(cuotaId).orElse(null);
    }

    // TODO: obtenerCuotasPorCliente
    @Override
    public List<Cuota> obtenerCuotasPorCliente(Long clienteId){
        // Buscamos la cuenta corriente del cliente
        CuentaCorriente cuentaCorriente = clienteRepository.findById(clienteId).get().getCuentaCorriente();

        // Buscamos las transacciones de la cuenta corriente
        List<Transaccion> transacciones = cuentaCorriente.getTransacciones();

        // Buscamos las cuotas de las transacciones
        List<Cuota> cuotas = obtenerCuotasPorTransaccion(transacciones.get(0).getId());

        return cuotas;
    }

    public List<CuotaDTO> obtenerCuotasPorDeudaMensual(Long deudaMensualId){
        // Buscar deuda mensual
        DeudaMensual deudaMensual = deudaMensualRepository.findById(deudaMensualId).get();
        // Buscar cuenta corriente
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(deudaMensual.getCuentaCorriente().getId()).get();

        List<Transaccion> transacciones = transaccionRepository.findByCuentaCorrienteAndFechaBetween(
                deudaMensual.getFechaInicioCiclo(), deudaMensual.getFechaFinCiclo(), cuentaCorriente.getId());

       // Buscar Cuotas de las transacciones
        List<Cuota> cuotas = new ArrayList();
        for (Transaccion transaccion : transacciones) {
            cuotas.addAll(obtenerCuotasPorTransaccion(transaccion.getId()));
        }

        // Filtrar las transacciones que son parte del mes de la deuda mensual
        // Filtrarlo por fecha de vencimiento
        cuotas = cuotas.stream().filter(cuota -> cuota.getFechaFinCiclo().equals(deudaMensual.getFechaFinCiclo())).collect(Collectors.toList());

        // Construir CuotaDTO

        List<CuotaDTO> cuotaDTOs = new ArrayList<>();
        for (Cuota cuota : cuotas) {
            CuotaDTO cuotaDTO = new CuotaDTO();
            cuotaDTO.setId(cuota.getId());
            cuotaDTO.setFechaVencimiento(cuota.getFechaVencimiento());
            cuotaDTO.setMonto(cuota.getMonto());
            cuotaDTO.setPagada(cuota.isPagada());
            cuotaDTOs.add(cuotaDTO);
        }
        return cuotaDTOs;
    }

}
