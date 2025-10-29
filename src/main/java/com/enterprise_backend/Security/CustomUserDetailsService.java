package com.enterprise_backend.Security;
import com.enterprise_backend.Entity.User;
import com.enterprise_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository repo) {
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User appUser = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole()));
        return org.springframework.security.core.userdetails.User.builder()
                .username(appUser.getCorreo())
                .password(appUser.getPassword())
                .authorities(authorities)
                .build();
    }
}
