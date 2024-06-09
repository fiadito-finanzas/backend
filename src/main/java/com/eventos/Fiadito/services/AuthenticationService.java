package com.eventos.Fiadito.services;

import com.eventos.Fiadito.dtos.AuthResponseDTO;
import com.eventos.Fiadito.dtos.UserAuthDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    public AuthResponseDTO login(UserAuthDTO userAuthDTO);

    public void loginSuccess(UserAuthDTO userAuthDTO);
}
