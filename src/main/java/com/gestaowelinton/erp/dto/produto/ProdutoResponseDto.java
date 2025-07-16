package com.gestaowelinton.erp.dto.produto;

import com.gestaowelinton.erp.model.Produto;

import java.util.List;
import java.util.stream.Collectors;

public record ProdutoResponseDto(
    Long idProduto,
    String nome,
    String codigoInterno,
    String unidadeMedida,
    String status,
    List<VariacaoProdutoDto> variacoes
) {
    // Construtor que converte a Entidade completa para o DTO de resposta
    public ProdutoResponseDto(Produto produto) {
        this(
            produto.getIdProduto(),
            produto.getNome(),
            produto.getCodigoInterno(),
            produto.getUnidadeMedida(),
            produto.getStatus(),
            // Converte a lista de entidades de variação para uma lista de DTOs de variação
            produto.getVariacoes()
                   .stream()
                   .map(VariacaoProdutoDto::new)
                   .collect(Collectors.toList())
        );
    }
}