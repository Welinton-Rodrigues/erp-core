package com.gestaowelinton.erp.dto.compra;

import java.math.BigDecimal;

public record ItemPedidoCompraRequestDto(
    Long idVariacaoProduto, // Qual variação específica está sendo comprada
    BigDecimal quantidade,
    BigDecimal precoCusto // Ao comprar, precisamos informar o custo do item
) {}