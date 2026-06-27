package br.com.guilhermedev.ecommerce.repository;

import br.com.guilhermedev.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
