package br.com.guilhermedev.ecommerce.service;

import br.com.guilhermedev.ecommerce.model.ItemPedido;
import br.com.guilhermedev.ecommerce.dto.ItemPedidoRequest;
import br.com.guilhermedev.ecommerce.dto.PedidoRequest;
import br.com.guilhermedev.ecommerce.model.Cliente;
import br.com.guilhermedev.ecommerce.model.ItemPedido;
import br.com.guilhermedev.ecommerce.model.Pedido;
import br.com.guilhermedev.ecommerce.model.Produto;
import br.com.guilhermedev.ecommerce.repository.ClienteRepository;
import br.com.guilhermedev.ecommerce.repository.PedidoRepository;
import br.com.guilhermedev.ecommerce.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Pedido realizarPedido(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));

        Pedido pedido = new Pedido(cliente);
        List<ItemPedido> itensDoPedido = new ArrayList<>();

        for (ItemPedidoRequest itemDTO : request.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemDTO.getProdutoId()));

            if (produto.getQuantidadeEstoque() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome() + ". Disponivel: " + produto.getQuantidadeEstoque());
            }

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemDTO.getQuantidade());
            produtoRepository.save(produto);

            ItemPedido item = new ItemPedido(pedido, produto, itemDTO.getQuantidade());
            produtoRepository.save(produto);
        }

        pedido.setItens(itensDoPedido);
        return pedidoRepository.save(pedido);
    }
}
