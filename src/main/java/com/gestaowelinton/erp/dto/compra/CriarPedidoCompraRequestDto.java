package com.gestaowelinton.erp.dto.compra;

import java.util.List;

public record CriarPedidoCompraRequestDto(
    Long idFornecedor, // O ID do fornecedor de quem estamos comprando
    String observacoes,
    List<ItemPedidoCompraRequestDto> itens // A lista de itens da compra
) {}