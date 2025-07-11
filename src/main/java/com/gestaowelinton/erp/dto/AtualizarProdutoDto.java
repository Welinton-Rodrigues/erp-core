package com.gestaowelinton.erp.dto;

import java.math.BigDecimal;

// Este é o DTO que seu Controller está procurando.
public record AtualizarProdutoDto(
    String nome,
    String codigoInterno,
    String unidadeMedida,
    BigDecimal precoVenda,
    String status
) {}