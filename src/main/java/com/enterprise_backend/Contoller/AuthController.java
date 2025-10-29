package com.enterprise_backend.Contoller;

import com.enterprise_backend.Entity.Cliente;
import com.enterprise_backend.Entity.User;
import com.enterprise_backend.Repository.UserRepository;
import com.enterprise_backend.Security.JwtUtil;
import com.enterprise_backend.dto.AuthResponse;
import com.enterprise_backend.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, UserRepository repo, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.authenticationManager = authManager;
        this.userRepository = repo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = encoder;
    }
    @CrossOrigin(origins ={"http://localhost:5173",
            "https://enterprise-front-production.up.railway.app"})

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        if (request.correo() == null || request.correo().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.correo(), request.password())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }

        User user = userRepository.findByCorreo(request.correo())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(user.getCorreo(), user.getRole());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer", user.getCorreo()));
    }

    // Endpoint para crear usuario (solo para pruebas/seed, proteger en producción)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        if (request.correo() == null || request.password() == null) {
            return ResponseEntity.badRequest().body("correo y password requeridos");
        }
        if (userRepository.findByCorreo(request.correo()).isPresent()) {
            return ResponseEntity.status(409).body("Usuario ya existe");
        }
        User u = new User();
        u.setCorreo(request.correo());
        u.setPassword(passwordEncoder.encode(request.password()));
        u.setRole(request.role());
        userRepository.save(u);
        return ResponseEntity.ok("Usuario creado");
    }
    @GetMapping("/users")
    public List<User> listar() {
        return userRepository.findAll();
    }


}

