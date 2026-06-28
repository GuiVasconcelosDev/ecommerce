package br.com.guilhermedev.ecommerce.controller;

import br.com.guilhermedev.ecommerce.dto.LoginRequest;
import br.com.guilhermedev.ecommerce.model.Cliente;
import br.com.guilhermedev.ecommerce.repository.ClienteRepository;
import br.com.guilhermedev.ecommerce.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private  final TokenService tokenService;

    public AuthController(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(request.getSenha(), cliente.getSenha())) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos");
        }

        String token = tokenService.gerarToken(cliente);
        return ResponseEntity.ok(token);
    }
}
