package com.gestaowelinton.erp.dto.pedido;

import com.gestaowelinton.erp.model.ItemPedidoVenda;

import java.math.BigDecimal;

public record ItemPedidoVendaResponseDto(
    Long idItemPedidoVenda,
    Long idProduto, // O ID do produto "pai"
    String nomeProduto,
    String cor,
    String tamanho,
    BigDecimal quantidade,
    BigDecimal precoUnitario,
    BigDecimal valorTotal
) {
    // Construtor que facilita a conversão da Entidade para o DTO
    public ItemPedidoVendaResponseDto(ItemPedidoVenda item) {
        this(
            item.getIdItemPedidoVenda(),
            item.getVariacaoProduto().getProduto().getIdProduto(), // Navegamos para pegar o ID do produto pai
            item.getVariacaoProduto().getProduto().getNome(),      // Pegamos o nome do produto pai
            item.getVariacaoProduto().getCor(),
            item.getVariacaoProduto().getTamanho(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getValorTotal()
        );
    }
}