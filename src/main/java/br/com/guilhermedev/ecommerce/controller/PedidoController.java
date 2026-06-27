package br.com.guilhermedev.ecommerce.controller;

import br.com.guilhermedev.ecommerce.dto.PedidoRequest;
import br.com.guilhermedev.ecommerce.model.Pedido;
import br.com.guilhermedev.ecommerce.repository.PedidoRepository;
import br.com.guilhermedev.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoRepository repository;

    public PedidoController(PedidoService service, PedidoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public Pedido criarPedido(@Valid @RequestBody PedidoRequest request) {
        return service.realizarPedido(request);
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
