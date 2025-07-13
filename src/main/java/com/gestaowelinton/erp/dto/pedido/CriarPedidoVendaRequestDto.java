package com.gestaowelinton.erp.dto.pedido;

import java.util.List;

// DTO para criar um novo pedido de venda completo
public record CriarPedidoVendaRequestDto(
    Integer idCliente, // O ID do cliente que está comprando
    // Adicionamos de volta os campos que faltavam no DTO
    String formaPagamento,
    String observacoes,
    List<ItemPedidoVendaRequestDto> itens // A lista de itens da venda
) {}