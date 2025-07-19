package com.gestaowelinton.erp.dto.financeiro;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarContaPagarDto(
    Long idEmpresa,
    Long idFornecedor, // Opcional
    String descricao,
    BigDecimal valor,
    LocalDate dataVencimento
) {}