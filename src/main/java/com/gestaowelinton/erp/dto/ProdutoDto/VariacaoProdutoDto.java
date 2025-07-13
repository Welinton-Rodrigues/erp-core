package com.gestaowelinton.erp.dto.ProdutoDto;

import com.gestaowelinton.erp.model.VariacaoProduto;

import java.math.BigDecimal;

public record VariacaoProdutoDto(
    Long idVariacaoProduto,
    String cor,
    String tamanho,
    String codigoBarras,
    BigDecimal precoVenda,
    Integer quantidadeEstoque
) {
    // Construtor que facilita a conversão da Entidade para o DTO
    public VariacaoProdutoDto(VariacaoProduto variacao) {
        this(
            variacao.getIdVariacaoProduto(),
            variacao.getCor(),
            variacao.getTamanho(),
            variacao.getCodigoBarras(),
            variacao.getPrecoVenda(),
            variacao.getQuantidadeEstoque()
        );
    }
}