package com.gestaowelinton.erp.dto.pedido;

import com.gestaowelinton.erp.model.PedidoVenda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoVendaResponseDto(
    Long idPedidoVenda,
    Integer idCliente,
    String nomeCliente,
    LocalDate dataPedido,
    String status,
    BigDecimal valorTotal,
    String formaPagamento,
    String observacoes,
    List<ItemPedidoVendaResponseDto> itens
) {
    // Construtor que converte a Entidade completa para o DTO de resposta
    public PedidoVendaResponseDto(PedidoVenda pedido) {
        this(
            pedido.getIdPedidoVenda(),
            pedido.getCliente().getIdCliente(),
            pedido.getCliente().getNomeRazaoSocial(),
            pedido.getDataPedido(),
            pedido.getStatus(),
            pedido.getValorTotal(),
            pedido.getFormaPagamento(),
            pedido.getObservacoes(),
            // Converte a lista de entidades de item para uma lista de DTOs de item
            pedido.getItens()
                  .stream()
                  .map(ItemPedidoVendaResponseDto::new)
                  .collect(Collectors.toList())
        );
    }
}