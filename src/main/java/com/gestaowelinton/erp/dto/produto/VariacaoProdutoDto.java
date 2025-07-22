package com.gestaowelinton.erp.dto.produto;

import com.gestaowelinton.erp.model.VariacaoProduto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;


public record VariacaoProdutoDto(
    
    Long idVariacaoProduto,
    String cor,
    String tamanho,

    @NotBlank(message = "O código de barras é obrigatório.")
    String codigoBarras,

    @NotNull(message = "O preço de venda não pode ser nulo.")
    @PositiveOrZero(message = "O preço de venda deve ser maior ou igual a zero.")
    BigDecimal precoVenda,

    @NotNull(message = "A quantidade em estoque não pode ser nula.")
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