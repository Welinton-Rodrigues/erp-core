package com.gestaowelinton.erp.dto.compra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CriarPedidoCompraRequestDto(
    @NotNull(message = "O ID do fornecedor é obrigatório.")
    Long idFornecedor,

    @Size(max = 1000, message = "As observações não podem exceder 1000 caracteres.")
    String observacoes,

    @NotEmpty(message = "O pedido de compra deve ter pelo menos um item.")
    @Valid // Valida cada item dentro da lista
    List<ItemPedidoCompraRequestDto> itens
) {}