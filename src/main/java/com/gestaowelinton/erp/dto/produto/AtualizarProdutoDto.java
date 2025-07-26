package com.gestaowelinton.erp.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AtualizarProdutoDto(
    @NotBlank(message = "O nome do produto não pode ficar em branco.")
    @Size(max = 255, message = "O nome não pode exceder 255 caracteres.")
    String nome,

    @Size(max = 50, message = "O código interno não pode exceder 50 caracteres.")
    String codigoInterno,

    @NotBlank(message = "A unidade de medida é obrigatória.")
    @Size(max = 10, message = "A unidade de medida não pode exceder 10 caracteres.")
    String unidadeMedida,

    // O preço de venda pode não ser obrigatório em uma atualização,
    // mas se for enviado, não pode ser negativo.
    @PositiveOrZero(message = "O preço de venda não pode ser negativo.")
    BigDecimal precoVenda,

    @NotBlank(message = "O status é obrigatório.")
    String status
) {}