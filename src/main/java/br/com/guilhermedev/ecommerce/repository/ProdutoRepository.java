package br.com.guilhermedev.ecommerce.repository;

import br.com.guilhermedev.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
