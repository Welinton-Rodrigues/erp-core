package com.gestaowelinton.erp.dto.compra;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero; // Import para números >= 0
import java.math.BigDecimal;

public record ItemPedidoCompraRequestDto(
    @NotNull(message = "O ID da variação do produto é obrigatório.")
    Long idVariacaoProduto,

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    BigDecimal quantidade,

    @NotNull(message = "O preço de custo é obrigatório.")
    @PositiveOrZero(message = "O preço de custo não pode ser negativo.")
    BigDecimal precoCusto
) {}