package com.gestaowelinton.erp.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive; // Import para números > 0
import java.math.BigDecimal;

public record ItemPedidoVendaRequestDto(
    @NotNull(message = "O ID da variação do produto é obrigatório.")
    Long idVariacaoProduto,

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    BigDecimal quantidade
) {}