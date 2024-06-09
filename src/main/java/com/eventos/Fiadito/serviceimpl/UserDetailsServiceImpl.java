package com.eventos.Fiadito.serviceimpl;


import com.eventos.Fiadito.models.Usuario;
import com.eventos.Fiadito.repositories.UserRepository;
import com.eventos.Fiadito.security.SecurityUser;
import com.eventos.Fiadito.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails getUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: "+username);
        }
        return new SecurityUser(user);
    }
}