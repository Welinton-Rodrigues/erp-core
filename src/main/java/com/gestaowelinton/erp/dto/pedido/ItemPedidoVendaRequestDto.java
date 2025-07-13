package com.gestaowelinton.erp.dto.pedido;

import java.math.BigDecimal;

// DTO para representar um item que está sendo comprado
public record ItemPedidoVendaRequestDto(
    Long idVariacaoProduto, // Qual variação específica está sendo vendida
    BigDecimal quantidade
) {}