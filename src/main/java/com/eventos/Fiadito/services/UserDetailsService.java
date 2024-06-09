package com.eventos.Fiadito.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {
    public UserDetails getUserByUsername(String username) throws UsernameNotFoundException;

}
