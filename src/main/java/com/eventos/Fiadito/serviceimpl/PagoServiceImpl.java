package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.PagoDTO;
import com.eventos.Fiadito.models.*;
import com.eventos.Fiadito.repositories.*;
import com.eventos.Fiadito.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DeudaMensualRepository deudaMensualRepository;

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;

    @Override
    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        // Buscar Cliente
        Optional<Cliente> cuentaEncontrada = clienteRepository.findById(pagoDTO.getClienteId());
        if (!cuentaEncontrada.isPresent()) {
            throw new RuntimeException("Cliente no encontrado");
        }
        // Buscar Deuda
        Optional<DeudaMensual> deudaEncontrada = deudaMensualRepository.findById(pagoDTO.getDeudaMensualId());
        if (!deudaEncontrada.isPresent()) {
            throw new RuntimeException("Deuda no encontrada");
        }

        // Validar que el monto del pago sea mayor a 0
        if (pagoDTO.getMonto() <= 0) {
            throw new RuntimeException("El monto del pago debe ser mayor a 0");
        }

        // Validar que el monto del pago sea menor o igual al monto de la deuda
        if (pagoDTO.getMonto() > deudaEncontrada.get().getMonto()) {
            throw new RuntimeException("El monto del pago no puede ser mayor al monto de la deuda");
        }
        // Implementamos las funciones
        if (pagoDTO.isCuota()) {
            return registrarPagoCuota(pagoDTO);
        } else {
            return registrarPagoMontoEspecifico(pagoDTO);
        }


    }

    //TODO: Validación de cliente, deuda y monto
    public boolean validarPago(PagoDTO pagoDTO) {
        // Buscar Cliente
        Optional<Cliente> cuentaEncontrada = clienteRepository.findById(pagoDTO.getClienteId());
        // Buscar Deuda
        Optional<DeudaMensual> deudaEncontrada = deudaMensualRepository.findById(pagoDTO.getClienteId());
        if (!cuentaEncontrada.isPresent()) {
            throw new RuntimeException("Cliente no encontrado");
        }
        if (!deudaEncontrada.isPresent()) {
            throw new RuntimeException("Deuda no encontrada");
        }

        if (pagoDTO.getMonto() <= 0) {
            throw new RuntimeException("El monto del pago debe ser mayor a 0");
        }

        if (pagoDTO.getMonto() > deudaEncontrada.get().getMonto()) {
            throw new RuntimeException("El monto del pago no puede ser mayor al monto de la deuda");

        }

        // Validar la cuota existe
        if (pagoDTO.isCuota()) {
            Optional<Cuota> cuotaEncontrada = cuotaRepository.findById(pagoDTO.getCuotaId());
            if (!cuotaEncontrada.isPresent()) {
                throw new RuntimeException("Cuota no encontrada");
            }
        }
        return true;
    }


    // TODO: Implementar funciones para pagar un monto en específico o pagar alguna cuota existente
    public PagoDTO registrarPagoMontoEspecifico(PagoDTO pagoDTO) {
        // Validacion
        if (validarPago(pagoDTO)) {

            // Buscamos la deuda
            Optional<DeudaMensual> deudaEncontrada = deudaMensualRepository.findById(pagoDTO.getDeudaMensualId());
            // Buscamos la cuenta
            Optional<CuentaCorriente> cuentaEncontrada = cuentaCorrienteRepository.findById(deudaEncontrada.get().getCuentaCorriente().getId());
            // Buscamos cliente
            Optional<Cliente> clienteEncontrado = clienteRepository.findById(pagoDTO.getClienteId());
            // Creamos el pago
            Pago pago = new Pago();
            pago.setMonto(pagoDTO.getMonto());
            pago.setMetodoPago(pagoDTO.getMetodoPago());
            pago.setFechaPago(new Date());
            pago.setCliente(clienteEncontrado.get());
            pago.setDeudaMensual(deudaEncontrada.get());
            pagoRepository.save(pago);
            // Actualizamos la deuda
            deudaEncontrada.get().setMonto(deudaEncontrada.get().getMonto() - pagoDTO.getMonto());
            // Si la deuda es igual a 0, entonces se cambia el estado de la deuda a pagada
            if (deudaEncontrada.get().getMonto() == 0) {
                deudaEncontrada.get().setPagada(true);
            }
            deudaMensualRepository.save(deudaEncontrada.get());

            // Devolver el saldo a la cuenta corriente
            cuentaEncontrada.get().setSaldoCredito(cuentaEncontrada.get().getSaldoCredito() + pagoDTO.getMonto());

            return pagoDTO;
        } else {
            throw new RuntimeException("No se pudo realizar el pago");
        }
    }

    // TODO: Implementar funcion para pagar una cuota existente
    public PagoDTO registrarPagoCuota(PagoDTO pagoDTO) {
        // Validacion
        if (validarPago(pagoDTO)) {
            // Buscamos la deuda
            Optional<DeudaMensual> deudaEncontrada = deudaMensualRepository.findById(pagoDTO.getDeudaMensualId());
            // Buscamos cliente
            Optional<Cliente> clienteEncontrado = clienteRepository.findById(pagoDTO.getClienteId());
            // Buscamos la cuenta
            Optional<CuentaCorriente> cuentaEncontrada = cuentaCorrienteRepository.findById(deudaEncontrada.get().getCuentaCorriente().getId());
            // Buscamos la cuota
            Optional<Cuota> cuotaEncontrada = cuotaRepository.findById(pagoDTO.getCuotaId());
            // Creamos el pago
            Pago pago = new Pago();
            pago.setMonto(pagoDTO.getMonto());
            pago.setMetodoPago(pagoDTO.getMetodoPago());
            pago.setFechaPago(new Date());
            pago.setCliente(clienteEncontrado.get());
            pago.setDeudaMensual(deudaEncontrada.get());
            pagoRepository.save(pago);
            // Actualizamos la deuda
            deudaEncontrada.get().setMonto(deudaEncontrada.get().getMonto() - pagoDTO.getMonto());
            // Si la deuda es igual a 0, entonces se cambia el estado de la deuda a pagada
            if (deudaEncontrada.get().getMonto() == 0) {
                deudaEncontrada.get().setPagada(true);
            }
            // Actualizamos la cuota
            cuotaEncontrada.get().setPagada(true);
            cuotaRepository.save(cuotaEncontrada.get());
            deudaMensualRepository.save(deudaEncontrada.get());

            // Devolver el saldo a la cuenta corriente
            cuentaEncontrada.get().setSaldoCredito(cuentaEncontrada.get().getSaldoCredito() + pagoDTO.getMonto());
            return pagoDTO;
        } else {
            throw new RuntimeException("No se pudo realizar el pago");
        }
        }

    //TODO: Obtener pagos por cuenta corriente
    public List<PagoDTO> obtenerPagosPorCuentaCorriente(Long cuentaCorrienteId) {
        // Buscar cuenta corriente
        Optional<CuentaCorriente> cuentaEncontrada = cuentaCorrienteRepository.findById(cuentaCorrienteId);
        if (!cuentaEncontrada.isPresent()) {
            throw new RuntimeException("Cuenta corriente no encontrada");
        }

        // Buscamos las deudas de la cuenta corriente

       List<DeudaMensual> deudasMensuales = cuentaEncontrada.stream().map(cuentaCorriente -> cuentaCorriente.getDeudasMensuales()).findFirst().get();

        // Buscamos los pagos de las deudas
        List<Pago> pagos = deudasMensuales.stream().map(deudaMensual -> deudaMensual.getPagos()).findFirst().get();

        // Mapeamos los pagos a DTO
        List<PagoDTO> pagosDTO = pagos.stream().map(pago -> {
            PagoDTO pagoDTO = new PagoDTO();
            pagoDTO.setMonto(pago.getMonto());
            pagoDTO.setMetodoPago(pago.getMetodoPago());
            pagoDTO.setFechaPago(pago.getFechaPago());
            pagoDTO.setClienteId(pago.getCliente().getId());
            pagoDTO.setDeudaMensualId(pago.getDeudaMensual().getId());
            return pagoDTO;
        }).collect(Collectors.toList());

        return pagosDTO;
    }

    // TODO: Obtener pagos por cliente
    public List<PagoDTO> obtenerPagosPorCliente(Long clienteId) {
        // Buscar cliente
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(clienteId);
        if (!clienteEncontrado.isPresent()) {
            throw new RuntimeException("Cliente no encontrado");
        }
        // Buscamos la cuenta corriente del cliente
        Optional<CuentaCorriente> cuentaEncontrada = cuentaCorrienteRepository.findById(clienteEncontrado.get().getCuentaCorriente().getId());

        // Buscamos las deudas mensuales de la cuenta corriente
        List<DeudaMensual> deudasMensuales = cuentaEncontrada.stream().map(cuentaCorriente -> cuentaCorriente.getDeudasMensuales()).findFirst().get();

        // Buscamos los pagos de las deudas
        List<Pago> pagos = deudasMensuales.stream().map(deudaMensual -> deudaMensual.getPagos()).findFirst().get();

        // Mapeamos los pagos a DTO
        List<PagoDTO> pagosDTO = pagos.stream().map(pago -> {
            PagoDTO pagoDTO = new PagoDTO();
            pagoDTO.setMonto(pago.getMonto());
            pagoDTO.setMetodoPago(pago.getMetodoPago());
            pagoDTO.setFechaPago(pago.getFechaPago());
            pagoDTO.setClienteId(pago.getCliente().getId());
            pagoDTO.setDeudaMensualId(pago.getDeudaMensual().getId());
            return pagoDTO;
        }).collect(Collectors.toList());

        return pagosDTO;
    }
}
