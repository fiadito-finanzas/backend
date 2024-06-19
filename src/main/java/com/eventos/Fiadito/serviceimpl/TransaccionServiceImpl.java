package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.TransaccionDTO;
import com.eventos.Fiadito.models.*;
import com.eventos.Fiadito.repositories.*;
import com.eventos.Fiadito.services.CuentaCorrienteService;
import com.eventos.Fiadito.services.TransaccionService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;

    @Autowired
    private DeudaMensualRepository deudaMensualRepository;

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public TransaccionDTO registrarTransaccion(TransaccionDTO transaccionDTO) {
        Optional<CuentaCorriente> optionalCuentaCorriente = cuentaCorrienteRepository.findById(transaccionDTO.getCuentaCorrienteId());
        if (optionalCuentaCorriente.isEmpty()) {
            throw new IllegalArgumentException("Cuenta corriente no encontrada");
        }
        CuentaCorriente cuentaCorriente = optionalCuentaCorriente.get();

        // Validar saldo de la cuenta corriente
        if (!validarSaldoCuentaCorriente(cuentaCorriente, transaccionDTO.getMonto())) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta corriente");
        }


        // Verificar si la transacción es COMPRA
        if (transaccionDTO.getTipo().equals("COMPRA")) {
            Double intereses = calcularInteres(transaccionDTO.getMonto(), cuentaCorriente.getTEP());
            Double montoTotal = calcularMontoTotal(transaccionDTO.getMonto(), intereses);
            // Crear transaccion
            Transaccion transaccion = new Transaccion();
            transaccion.setCuentaCorriente(cuentaCorriente);
            transaccion.setFecha(new Date());
            transaccion.setMonto(transaccionDTO.getMonto());
            transaccion.setInteres(intereses);
            transaccion.setTotalMonto(montoTotal);
            transaccion.setTipo(transaccionDTO.getTipo());
            transaccionRepository.save(transaccion);

            // Actualizar saldo de la cuenta corriente
            cuentaCorriente.setSaldoCredito(cuentaCorriente.getSaldoCredito() - montoTotal);
            cuentaCorrienteRepository.save(cuentaCorriente);
            // Añadir transaccion a cuentacorriente
            cuentaCorriente.getTransacciones().add(transaccion);

            // Verificar si hay una deuda mensual para el mes actual
            Date fechaMensual = cuentaCorriente.getFechaPagoMensual();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaMensual);
            calendar.add(Calendar.MONTH, -1);
            Date fechaMesAnterior = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date fechaFin = calendar.getTime();
            DeudaMensual deudaMensualExiste = deudaMensualRepository.findByCuentaCorrienteAndFechaInicioCicloAndFechaFinCiclo(cuentaCorriente, fechaMesAnterior, fechaFin);

            // Validar si la deuda mensual ya existe
            if (deudaMensualExiste == null) {
                DeudaMensual deudaMensual = new DeudaMensual();
                deudaMensual.setCuentaCorriente(cuentaCorriente);
                deudaMensual.setMonto(montoTotal);
                // Considerar el inicio de ciclo la fecha de inicio mensual de cuenta corriente
                // Considerar el mes pasado de la fecha de inicio mensual de cuenta corriente como el inicio de ciclo
                Date fechaPagoMensual = cuentaCorriente.getFechaPagoMensual();
                calendar.setTime(fechaPagoMensual);
                calendar.add(Calendar.MONTH, -1);
                deudaMensual.setFechaInicioCiclo(calendar.getTime());
                // Considerar el fin de ciclo la fecha de pago mensual
                deudaMensual.setFechaFinCiclo(cuentaCorriente.getFechaPagoMensual());
                deudaMensual.setMonto(montoTotal);
                deudaMensual.setInteres(intereses);
                deudaMensual.setPagada(false);
                // El mes actual
                deudaMensual.setFechaTransaccion(new Date());
                // Guardar la deuda mensual
                deudaMensualRepository.save(deudaMensual);
            } else {
                // Si existe una deuda mensual, se actualiza el monto
                DeudaMensual deudaMensual = deudaMensualExiste;
                deudaMensual.setMonto(deudaMensual.getMonto() + montoTotal);
                deudaMensual.setInteres(deudaMensual.getInteres() + intereses);
                deudaMensual.setFechaTransaccion(new Date());
                deudaMensualRepository.save(deudaMensual);
            }

            // Crear el DTO
            TransaccionDTO nuevatransaccion = new TransaccionDTO();
            nuevatransaccion.setId(transaccion.getId());
            nuevatransaccion.setCuentaCorrienteId(transaccion.getCuentaCorriente().getId());
            nuevatransaccion.setFecha(transaccion.getFecha());
            nuevatransaccion.setMonto(transaccion.getMonto());
            nuevatransaccion.setTipo(transaccion.getTipo());
            nuevatransaccion.setInteres(transaccion.getInteres());
            nuevatransaccion.setCuotas(1);
            return nuevatransaccion;
        } else if (transaccionDTO.getTipo().equals("COMPRA_A_CUOTAS")) {
            // Validar si hay saldo suficiente
            if (!validarSaldoCuentaCorriente(cuentaCorriente, transaccionDTO.getMonto())) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta corriente");
            }
            // Validar si el número de cuotas es mayor a 0
            if (transaccionDTO.getCuotas() <= 0) {
                throw new IllegalArgumentException("Número de cuotas inválido");
            }
            // Validar si la cantidad de plazo gracia T es mayor o igual a 0
            if (transaccionDTO.getCantidadPlazoGraciaT() < 0) {
                throw new IllegalArgumentException("Cantidad de plazo gracia T inválida");
            }
            // Validar si la cantidad de plazo gracia P es mayor o igual a 0
            if (transaccionDTO.getCantidadPlazoGraciaP() < 0) {
                throw new IllegalArgumentException("Cantidad de plazo gracia P inválida");
            }
            //Validar si la cantidad de cuotas es mayor o igual a la suma de plazo gracia T y plazo gracia P
            if (transaccionDTO.getCuotas() < transaccionDTO.getCantidadPlazoGraciaT() + transaccionDTO.getCantidadPlazoGraciaP()) {
                throw new IllegalArgumentException("Cantidad de cuotas inválida");
            }

            // Variables para el leasing francés
            // TEP -> Tasa Efectiva Periodica
            // n -> Número de cuotas
            // frecuencia de pago
            // n° dias del año
            // n° cuotas por año
            // n° total de cuotas
            // amortización
            // intereses
            // cuota
            // flujo
            // saldo final
            // Plazo Gracia -> T: Total, P: Parcial, S: Si

            // Calcular la tasa efectiva mensual
            double TEP = cuentaCorriente.getTEP();
            // Calcular el número de cuotas
            int n = transaccionDTO.getCuotas();
            // Calcular la frecuencia de pago
            int frecuenciaPago = 30;
            // Calcular el número de días del año
            int diasAnio = 360;
            // Calcular el número de cuotas por año
            int cuotasAnio = 12;
            // Calcular el número total de cuotas
            int totalCuotas = n;
            // Cuota actual
            int cuota = 1;
            int contadorPlazoGraciaT = 0;
            int contadorPlazoGraciaP = 0;
            // Verificar nro de cutoas
            if (n <= 0) {
                throw new IllegalArgumentException("Número de cuotas inválido");
            }

            // Crear la transacción
            Transaccion transaccion = new Transaccion();
            transaccion.setCuentaCorriente(cuentaCorriente);
            transaccion.setFecha(new Date());
            transaccion.setMonto(transaccionDTO.getMonto());
            transaccion.setInteres(0.00);
            transaccion.setTotalMonto(0.00);
            transaccion.setTipo(transaccionDTO.getTipo());
            transaccionRepository.save(transaccion);
            // Añadir transaccion a cuentacorriente
            cuentaCorriente.getTransacciones().add(transaccion);

            Calendar fechaVencimiento = Calendar.getInstance();
            fechaVencimiento.setTime(cuentaCorriente.getFechaPagoMensual());
            Calendar fechaInicioCiclo = Calendar.getInstance();
            fechaInicioCiclo.setTime(cuentaCorriente.getFechaPagoMensual());
            fechaInicioCiclo.add(Calendar.MONTH, -1);
            // Hacer recorrido por cada nro de cuota
            Double saldoFinal = 0.00;
            // En la primera pasada, se considera el saldo inicial al del monto, luego es diferente
            Double saldoInicial = 0.00;
            Double montoTotal = 0.00;
            Double interesesTotal = 0.00;
            for (int i = 0; i < n; i++) {
                Double montocuota = 0.00;
                Double saldoInicialIndexado = 0.00;
                Double amortizacion = 0.00;
                Double interes = 0.00;
                String gracia = "";
                if (contadorPlazoGraciaT < transaccionDTO.getCantidadPlazoGraciaT()) {
                    gracia = "T";
                    contadorPlazoGraciaT++;
                } else if (contadorPlazoGraciaP < transaccionDTO.getCantidadPlazoGraciaP() && contadorPlazoGraciaT == transaccionDTO.getCantidadPlazoGraciaT()) {
                    gracia = "P";
                    contadorPlazoGraciaP++;
                } else {
                    gracia = "S";
                }

                if (i == 0) {
                    // Calcular el saldo inicial
                    saldoInicial = transaccionDTO.getMonto();
                } else {
                    // Calcular el saldo inicial
                    saldoInicial = saldoFinal;
                }
                // Calcular el saldo inicial indexado
                saldoInicialIndexado = saldoInicial;
                // Calcular el interés
                interes = calcularInteresLeasingFrances(saldoInicialIndexado, TEP);
                // Calcular la cuota
                montocuota = calcularCuotaLeasingFrances(saldoInicialIndexado, TEP, cuota, totalCuotas, interes, gracia);
                // Calcular la amortización
                amortizacion = calcularAmortizacionLeasingFrances(montocuota, interes, gracia);
                // Calcular el saldo final
                if (gracia.equals("T")) {
                    saldoFinal = saldoInicial + interes;
                } else {
                    saldoFinal = saldoInicial - amortizacion;
                }

                // Crear Cuota
                Cuota cuotaObj = new Cuota();
                cuotaObj.setTransaccion(transaccion);
                // La fecha de vencimiento es la fecha de pago mensual de la cuenta y esta irá aumentando
                // según la frecuencia de pago
                cuotaObj.setMonto(montocuota);
                cuotaObj.setPagada(false);
                cuotaObj.setFechaVencimiento(fechaVencimiento.getTime());
                cuotaObj.setFechaFinCiclo(fechaVencimiento.getTime());
                cuotaObj.setFechaInicioCiclo(fechaInicioCiclo.getTime());
                cuotaObj.setPeriodoGracia(gracia);
                cuotaObj.setMontoInteres(interes);
                cuotaObj.setMontoAmortizacion(amortizacion);
                // Guardar la cuota
                cuotaRepository.save(cuotaObj);
                // Validar si existe DeudaMensual, si no existe crearlo. Luego, añadir el monto de la cuota correspondiente
                // En caso de que exista, se actualiza el monto, para las demás cuotas, se deben de crear deudas mensuales
                // con los montos correspondientes
                DeudaMensual deudaMensual = deudaMensualRepository.findByCuentaCorrienteAndFechaInicioCicloAndFechaFinCiclo(cuentaCorriente, fechaInicioCiclo.getTime(), fechaVencimiento.getTime());
                if (deudaMensual == null) {
                    // Crear si no existe
                    DeudaMensual deudaMensualNueva = new DeudaMensual();
                    deudaMensualNueva.setCuentaCorriente(cuentaCorriente);
                    deudaMensualNueva.setFechaTransaccion(new Date());
                    deudaMensualNueva.setFechaInicioCiclo(fechaInicioCiclo.getTime());
                    deudaMensualNueva.setFechaFinCiclo(fechaVencimiento.getTime());
                    deudaMensualNueva.setMonto(montocuota);
                    deudaMensualNueva.setInteres(interes);
                    deudaMensualNueva.setPagada(false);
                    deudaMensualRepository.save(deudaMensualNueva);

                } else {
                    // Actualizar si existe
                    deudaMensual.setMonto(deudaMensual.getMonto() + montocuota);
                    deudaMensual.setInteres(deudaMensual.getInteres() + interes);
                    deudaMensual.setFechaTransaccion(new Date());
                    deudaMensualRepository.save(deudaMensual);
                }

                fechaVencimiento.add(Calendar.MONTH, 1);
                // Ahora debe aumentar la fecha de inicio de ciclo
                fechaInicioCiclo.add(Calendar.MONTH, 1);
                cuota++;
                montoTotal += montocuota;
                interesesTotal += interes;

                // Actualizar monto total de la transacción
                // Colocar variables para montoTotal y montoInteres
                transaccion.setInteres(interesesTotal);
                transaccion.setTotalMonto(montoTotal);
                transaccionRepository.save(transaccion);
            }

            // Construir DTO
            TransaccionDTO nuevatransaccion = new TransaccionDTO();
            nuevatransaccion.setId(transaccion.getId());
            nuevatransaccion.setCuentaCorrienteId(transaccion.getCuentaCorriente().getId());
            nuevatransaccion.setCantidadPlazoGraciaT(transaccionDTO.getCantidadPlazoGraciaT());
            nuevatransaccion.setCantidadPlazoGraciaP(transaccionDTO.getCantidadPlazoGraciaP());
            nuevatransaccion.setInteres(transaccion.getInteres());
            nuevatransaccion.setCuotas(n);
            nuevatransaccion.setFecha(transaccion.getFecha());
            nuevatransaccion.setMonto(transaccion.getMonto());
            nuevatransaccion.setTipo(transaccion.getTipo());
            return nuevatransaccion;
        } else {
            throw new IllegalArgumentException("Tipo de transacción no soportado");
        }
    }

    // TODO: Obtener transacciones por cuenta corriente
    public List<TransaccionDTO> obtenerTransaccionesPorCuentaCorriente(Long cuentaCorriente) {
        // Encontrar la cuenta corriente
        Optional<CuentaCorriente> optionalCuentaCorriente = cuentaCorrienteRepository.findById(cuentaCorriente);
        if (optionalCuentaCorriente.isEmpty()) {
            throw new IllegalArgumentException("Cuenta corriente no encontrada");
        }
        CuentaCorriente cuentaCorrienteObj = optionalCuentaCorriente.get();
        // Encontrar las transacciones por cuenta corriente
        List<Transaccion> transacciones = transaccionRepository.findByCuentaCorriente(cuentaCorrienteObj);

        // Convertir a DTO
        List<TransaccionDTO> transaccionesDTO = transacciones.stream().map(transaccion -> {
            // Contar las cuotas que tiene la transacción si es de tipo CUOTA_A_CUOTAS
            int cuotas = 0;
            if (transaccion.getTipo().equals("COMPRA_A_CUOTAS")) {
                cuotas = cuotaRepository.findByTransaccionId(transaccion.getId()).size();
            }
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setCuentaCorrienteId(transaccion.getCuentaCorriente().getId());
            transaccionDTO.setFecha(transaccion.getFecha());
            transaccionDTO.setMonto(transaccion.getMonto());
            transaccionDTO.setTipo(transaccion.getTipo());
            transaccionDTO.setInteres(transaccion.getInteres());
            transaccionDTO.setCuotas(cuotas);
            return transaccionDTO;
        }).collect(Collectors.toList());
        return transaccionesDTO;
    }

    //TODO: Obtener transacciones por establecimiento
    public List<Transaccion> obtenerTransaccionesPorEstablecimiento(Long establecimientoId) {
        // Encontrar las transacciones por establecimiento
        // Primero buscar los usuarios del establecimiento
        // Luego buscar las cuentas corrientes de los usuarios
        // Luego buscar las transacciones de las cuentas corrientes
        List<Cliente> clientes = clienteRepository.findByEstablecimientoId(establecimientoId);

        // Ahora buscar cuentas corrientes
        List<CuentaCorriente> cuentasCorrientes = clientes.stream().map(Cliente::getCuentaCorriente).collect(Collectors.toList());

        // Ahora buscar las transacciones
        List<Transaccion> transacciones = cuentasCorrientes.stream().map(cuentaCorriente -> transaccionRepository.findByCuentaCorriente(cuentaCorriente)).flatMap(List::stream).collect(Collectors.toList());

        return transacciones;
    }

    //TODO: Obtener transaccion por ID
    public Transaccion obtenerTransaccionPorId(Long transaccionId) {
        Optional<Transaccion> optionalTransaccion = transaccionRepository.findById(transaccionId);
        if (optionalTransaccion.isEmpty()) {
            throw new IllegalArgumentException("Transacción no encontrada");
        }
        return optionalTransaccion.get();
    }

    // TODO: Obtener transacciones por fecha
    public List<TransaccionDTO> obtenerTransaccionesEntreFechas(String fechaInicio, String fechaFin) {
        // Convertir las fechas a Date
        Date fechaInicioDate = new Date(fechaInicio);
        Date fechaFinDate = new Date(fechaFin);
        // Listar las transacciones
        List<Transaccion> transacciones = transaccionRepository.findByFechaBetween(fechaInicioDate, fechaFinDate);
        // Mapear las transacciones
        List<TransaccionDTO> transaccionesDTO = transacciones.stream().map(transaccion -> {
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setCuentaCorrienteId(transaccion.getCuentaCorriente().getId());
            transaccionDTO.setFecha(transaccion.getFecha());
            transaccionDTO.setMonto(transaccion.getMonto());
            transaccionDTO.setTipo(transaccion.getTipo());
            transaccionDTO.setInteres(transaccion.getInteres());
            return transaccionDTO;
        }).collect(Collectors.toList());

        return transaccionesDTO;
    }
    
    private double calcularInteres(double monto, double tasaInteres) {
        // Considerando que estamos tomando la tasa Efectiva mensual
        // Valor Futuro = Valor actual * (1 + TEP) ^ n
        double VF = monto * (1 + tasaInteres);
        return VF - monto;
    }
    
    private double calcularMontoTotal(double monto, double interes) {
        return monto + interes;
    }

    // Función para validar el saldo de la cuenta corriente
    private boolean validarSaldoCuentaCorriente(CuentaCorriente cuentaCorriente, double monto) {
        return cuentaCorriente.getSaldoCredito() >= monto;
    }

    // Leasing Francés
    // Calcular saldo inicial

    // Calcular interes
    private Double calcularInteresLeasingFrances(Double saldoInicialIndexado, Double TEP) {
        return saldoInicialIndexado * TEP;
    }

    // Calcular amortizacion
    private Double calcularAmortizacionLeasingFrances(Double cuota, Double interes, String plazoGracia) {
        if (plazoGracia.equals("T")) {
            return 0.0;
        } else if (plazoGracia.equals("P")) {
            return 0.0;
        } else {
            return cuota - interes;
        }
    }

    // Calcular cuota
    private Double calcularCuotaLeasingFrances(Double saldoInicialIndexado, Double tasaperiodo, int numerocuota, int maxcuotas, Double interes, String plazoGracia) {
        int nc = numerocuota;
        int n = maxcuotas;
        Double TEP = tasaperiodo;

        // Validar que la cuota actual sea menor o igual al número total de cuotas
        if (nc > n) {
            return 0.00;
        }

        if (plazoGracia.equals("T")) {
            return 0.00;
        } else if (plazoGracia.equals("P")) {
            return interes;
        } else {
            // Método Vencido General Frances
            // R = SI * (TEP * (1+TEP)^(n-nc+1))/(1+TEP)^(n-nc+1)-1
            Double SI = saldoInicialIndexado;
            // Mejorar el POW
            double pow = Math.pow(1 + TEP, n - nc + 1);
            Double R = SI * (TEP * pow) / (pow - 1);
            return R;
        }


    }



}
