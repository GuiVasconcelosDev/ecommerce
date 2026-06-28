package br.com.guilhermedev.ecommerce.controller;

import br.com.guilhermedev.ecommerce.model.Cliente;
import br.com.guilhermedev.ecommerce.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ClienteController(ClienteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente criar(@Valid @RequestBody Cliente cliente) {

        String senhaCripto = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCripto);

        return repository.save(cliente);
    }
}
