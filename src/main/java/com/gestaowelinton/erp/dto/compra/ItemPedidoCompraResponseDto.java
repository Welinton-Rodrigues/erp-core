package com.gestaowelinton.erp.dto.compra;

import com.gestaowelinton.erp.model.ItemPedidoCompra;

import java.math.BigDecimal;

public record ItemPedidoCompraResponseDto(
    Long idItemPedidoCompra,
    Long idVariacaoProduto,
    String nomeProduto,
    BigDecimal quantidade,
    BigDecimal precoCusto,
    BigDecimal valorTotal
) {
    public ItemPedidoCompraResponseDto(ItemPedidoCompra item) {
        this(
            item.getIdItemPedidoCompra(),
            item.getVariacaoProduto().getIdVariacaoProduto(),
            item.getVariacaoProduto().getProduto().getNome() + " (" + item.getVariacaoProduto().getCor() + " / " + item.getVariacaoProduto().getTamanho() + ")",
            item.getQuantidade(),
            item.getPrecoCusto(),
            item.getValorTotal()
        );
    }
}