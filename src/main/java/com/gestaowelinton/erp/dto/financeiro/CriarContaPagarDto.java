package com.gestaowelinton.erp.dto.financeiro;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarContaPagarDto(
    @NotNull(message = "O ID da empresa é obrigatório.")
    Long idEmpresa,

    Long idFornecedor, // Opcional, sem validação de obrigatoriedade

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres.")
    String descricao,

    @NotNull(message = "O valor é obrigatório.")
    @Positive(message = "O valor deve ser um número positivo.")
    BigDecimal valor,

    @NotNull(message = "A data de vencimento é obrigatória.")
    @FutureOrPresent(message = "A data de vencimento não pode ser uma data passada.")
    LocalDate dataVencimento
) {}