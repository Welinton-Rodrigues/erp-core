package com.gestaowelinton.erp.dto.produto;

import java.util.List;

// Este é o "formulário" completo para criar um produto e suas variações
public record CriarProdutoDto(
    // Dados do Produto "Pai"
    Long idEmpresa,
    String nome,
    String codigoInterno,
    String unidadeMedida,
    String status,

    // Lista com as variações
    List<VariacaoProdutoDto> variacoes
) {}