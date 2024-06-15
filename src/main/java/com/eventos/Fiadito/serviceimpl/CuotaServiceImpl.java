package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.models.Cuota;
import com.eventos.Fiadito.models.DeudaMensual;
import com.eventos.Fiadito.models.Transaccion;
import com.eventos.Fiadito.repositories.ClienteRepository;
import com.eventos.Fiadito.repositories.CuotaRepository;
import com.eventos.Fiadito.services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuotaServiceImpl implements CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;
    @Autowired
    private ClienteRepository clienteRepository;



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

}
