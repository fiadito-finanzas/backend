package com.eventos.Fiadito.serviceimpl;

import com.eventos.Fiadito.dtos.CuentaCorrienteDTO;
import com.eventos.Fiadito.models.Cliente;
import com.eventos.Fiadito.models.CuentaCorriente;
import com.eventos.Fiadito.repositories.ClienteRepository;
import com.eventos.Fiadito.repositories.CuentaCorrienteRepository;
import com.eventos.Fiadito.repositories.TransaccionRepository;
import com.eventos.Fiadito.services.CuentaCorrienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CuentaCorrienteServiceImpl implements CuentaCorrienteService {

    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    public CuentaCorrienteDTO crearCuentaCorriente(CuentaCorrienteDTO cuentaCorrienteDTO) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(cuentaCorrienteDTO.getClienteId());
        if (optionalCliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }
        Cliente cliente = optionalCliente.get();

        double m = calcularCapitalizacion(cuentaCorrienteDTO.getPeriodoCapitalizacion().toLowerCase());
        double n = m;
        double TEP = 0.0;
        if (cuentaCorrienteDTO.getTipoInteres().equals("Efectiva")) {
            TEP = cuentaCorrienteDTO.getTasaInteres()*0.01;
        }
        else if (cuentaCorrienteDTO.getTipoInteres().equals("Nominal")) {
            TEP = calcularTEP(cuentaCorrienteDTO.getTasaInteres(),m,n);
        }
        else {
            throw new IllegalArgumentException("Tipo de interés no soportado: " + cuentaCorrienteDTO.getTipoInteres());
        }

        // Crear cuenta corriente
        CuentaCorriente cuentaCorriente = new CuentaCorriente();
        cuentaCorriente.setCliente(cliente);
        cuentaCorriente.setTasaInteres(cuentaCorrienteDTO.getTasaInteres());
        cuentaCorriente.setTasaMoratoria(cuentaCorrienteDTO.getTasaMoratoria());
        cuentaCorriente.setSaldoCredito(cuentaCorrienteDTO.getSaldoCredito());
        cuentaCorriente.setFechaPagoMensual(cuentaCorrienteDTO.getFechaPagoMensual());
        cuentaCorriente.setTipoInteres(cuentaCorrienteDTO.getTipoInteres());
        cuentaCorriente.setPeriodoCapitalizacion(cuentaCorrienteDTO.getPeriodoCapitalizacion());
        cuentaCorriente.setFechaCreacion(new Date());
        cuentaCorriente.setFechaUltimaActualizacion(new Date());
        cuentaCorriente.setM(m);
        cuentaCorriente.setN(n);
        cuentaCorriente.setTEP(TEP);
        cuentaCorrienteRepository.save(cuentaCorriente);
        // Crear DTO
        CuentaCorrienteDTO cuentaCreada = new CuentaCorrienteDTO();
        cuentaCreada.setId(cuentaCorriente.getId());
        cuentaCreada.setClienteId(cuentaCorriente.getCliente().getId());
        cuentaCreada.setTasaInteres(cuentaCorriente.getTasaInteres());
        cuentaCreada.setTasaMoratoria(cuentaCorriente.getTasaMoratoria());
        cuentaCreada.setSaldoCredito(cuentaCorriente.getSaldoCredito());
        cuentaCreada.setFechaPagoMensual(cuentaCorriente.getFechaPagoMensual());
        cuentaCreada.setTipoInteres(cuentaCorriente.getTipoInteres());
        cuentaCreada.setPeriodoCapitalizacion(cuentaCorriente.getPeriodoCapitalizacion());
        return cuentaCreada;
    }

    public double calcularCapitalizacion(String periodo) {
        switch (periodo) {
            case "diario":
                return 30;
            case "quincenal":
                return 2;
            case "mensual":
                return 1;
            case "bimestral":
                return Double.valueOf(30.0/60.0);
            case "trimestral":
                return Double.valueOf(30.0/90.0);
            case "cuatrimestral":
                return Double.valueOf(30.0/120.0);
            case "semestral":
                return Double.valueOf(30.0/180.0);
            case "anual":
                return Double.valueOf(30.0/360.0);
            default:
                throw new IllegalArgumentException("Periodo de capitalización no soportado: " + periodo);
        }
    }

    public double calcularTEP(double tn,double M,double N) {
        // TEP = ( 1 + TN / m ) ^ n - 1
        double TN = tn*0.01;
        double m = M;
        double n = N;

        double tep = Math.pow(1 + (TN / m), n)-1;
        return tep;
    }

}
