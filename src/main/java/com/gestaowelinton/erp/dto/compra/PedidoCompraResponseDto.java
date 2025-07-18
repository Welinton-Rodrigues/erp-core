package com.gestaowelinton.erp.dto.compra;

import com.gestaowelinton.erp.model.PedidoCompra;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoCompraResponseDto(
    Long idPedidoCompra,
    Long idFornecedor,
    String nomeFornecedor,
    LocalDate dataPedido,
    String status,
    BigDecimal valorTotal,
    String observacoes,
    List<ItemPedidoCompraResponseDto> itens
) {
    public PedidoCompraResponseDto(PedidoCompra pedido) {
        this(
            pedido.getIdPedidoCompra(),
            pedido.getFornecedor().getIdFornecedor(),
            pedido.getFornecedor().getNomeRazaoSocial(),
            pedido.getDataPedido(),
            pedido.getStatus(),
            pedido.getValorTotal(),
            pedido.getObservacoes(),
            pedido.getItens().stream().map(ItemPedidoCompraResponseDto::new).collect(Collectors.toList())
        );
    }
}