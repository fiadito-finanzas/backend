package com.eventos.Fiadito.serviceimpl;


import com.eventos.Fiadito.dtos.AuthResponseDTO;
import com.eventos.Fiadito.dtos.UserAuthDTO;
import com.eventos.Fiadito.jwt.JwtService;
import com.eventos.Fiadito.models.Usuario;
import com.eventos.Fiadito.repositories.UserRepository;
import com.eventos.Fiadito.security.SecurityUser;
import com.eventos.Fiadito.services.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponseDTO login(UserAuthDTO userAuthDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword()));
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(userAuthDTO.getUsername());
        loginSuccess(userAuthDTO);
        if (securityUser == null) {
            throw new RuntimeException("User not found");
        }
        String jwt = jwtService.generateToken(securityUser);
        return new AuthResponseDTO(jwt);
    }

    @Override
    @Transactional
    public void loginSuccess(UserAuthDTO userAuthDTO) {
        Usuario userFound = userRepository.findByUsername(userAuthDTO.getUsername());
        if (userFound == null) {
            throw new RuntimeException("User not found");
        }
        userRepository.save(userFound);
    }


}
