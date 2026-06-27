package br.com.guilhermedev.ecommerce.repository;

import br.com.guilhermedev.ecommerce.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
