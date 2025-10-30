package com.enterprise_backend.contoller;

import com.enterprise_backend.Contoller.AuthController;
import com.enterprise_backend.Entity.User;
import com.enterprise_backend.Repository.UserRepository;
import com.enterprise_backend.Security.JwtUtil;
import com.enterprise_backend.dto.AuthResponse;
import com.enterprise_backend.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

// Tus clases del proyecto

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController controller;



    @Test
    void login_deberiaRetornar400_cuandoCorreoEsNulo() {
        LoginRequest request = new LoginRequest(null, "pass", "USER");
        var response = controller.login(request);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void login_deberiaRetornar400_cuandoCorreoEstaVacio() {
        LoginRequest request = new LoginRequest("", "pass", "USER");
        var response = controller.login(request);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void login_deberiaRetornar401_cuandoCredencialesSonInvalidas() {
        LoginRequest request = new LoginRequest("user@test.com", "wrong", "USER");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid"));

        var response = controller.login(request);
        assertThat(response.getStatusCode().value()).isEqualTo(401);
    }

    @Test
    void login_deberiaRetornarToken_cuandoCredencialesSonValidas() {
        String correo = "user@test.com";
        LoginRequest request = new LoginRequest(correo, "pass", "USER");
        User user = new User();
        user.setCorreo(correo);
        user.setRole("USER");

        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userRepository.findByCorreo(correo)).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(correo, "USER")).thenReturn("fake-jwt-token");

        var response = controller.login(request);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isEqualTo("fake-jwt-token");
    }

    @Test
    void login_deberiaLanzarExcepcion_cuandoUsuarioNoExiste() {
        String correo = "noexiste@test.com";
        LoginRequest request = new LoginRequest(correo, "pass", "USER");
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        try {
            controller.login(request);
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Usuario no encontrado");
        }
    }



    @Test
    void register_deberiaRetornar400_cuandoCorreoEsNulo() {
        LoginRequest request = new LoginRequest(null, "pass", "USER");
        var response = controller.register(request);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("correo y password requeridos");
    }

    @Test
    void register_deberiaRetornar400_cuandoPasswordEsNulo() {
        LoginRequest request = new LoginRequest("user@test.com", null, "USER");
        var response = controller.register(request);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("correo y password requeridos");
    }

    @Test
    void register_deberiaRetornar409_cuandoUsuarioYaExiste() {
        LoginRequest request = new LoginRequest("existente@test.com", "pass", "USER");
        when(userRepository.findByCorreo("existente@test.com")).thenReturn(Optional.of(new User()));

        var response = controller.register(request);
        assertThat(response.getStatusCode().value()).isEqualTo(409);
        assertThat(response.getBody()).isEqualTo("Usuario ya existe");
    }

    @Test
    void register_deberiaCrearUsuarioYRetornar200() {
        LoginRequest request = new LoginRequest("nuevo@test.com", "pass", "USER");
        when(userRepository.findByCorreo("nuevo@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encoded-pass");

        var response = controller.register(request);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Usuario creado");
        verify(userRepository).save(any(User.class));
    }
}