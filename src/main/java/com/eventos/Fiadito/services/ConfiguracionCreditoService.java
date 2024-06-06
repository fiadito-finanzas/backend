package com.eventos.Fiadito.services;

import com.eventos.Fiadito.models.ConfiguracionCredito;
import org.springframework.stereotype.Service;

@Service
public interface ConfiguracionCreditoService {

    ConfiguracionCredito crearConfiguracionCredito(ConfiguracionCredito configuracionCredito);
    ConfiguracionCredito actualizarConfiguracionCredito(ConfiguracionCredito configuracionCredito);

}
