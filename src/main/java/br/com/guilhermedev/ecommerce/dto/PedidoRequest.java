package br.com.guilhermedev.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequest {

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    @Valid
    private List<ItemPedidoRequest> itens;

    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }
}
