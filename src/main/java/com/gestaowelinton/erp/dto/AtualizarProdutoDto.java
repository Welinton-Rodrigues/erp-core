package com.gestaowelinton.erp.dto;

import java.math.BigDecimal;

// Este record define os campos que a API pode receber para uma atualização.
public record AtualizarProdutoDto(
    String nome,
    String codigoInterno,
    String unidadeMedida,
    BigDecimal precoVenda,
    String status
) {}