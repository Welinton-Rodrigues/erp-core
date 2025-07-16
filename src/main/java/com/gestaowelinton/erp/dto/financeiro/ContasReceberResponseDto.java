package com.gestaowelinton.erp.dto.financeiro;

import com.gestaowelinton.erp.model.ContasReceber;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContasReceberResponseDto(
    Long idContaReceber,
    String descricao,
    BigDecimal valor,
    LocalDate dataVencimento,
    LocalDate dataRecebimento,
    String status,
    Integer idCliente,
    String nomeCliente,
    Long idPedidoVenda
) {
    public ContasReceberResponseDto(ContasReceber conta) {
        this(
            conta.getIdContaReceber(),
            conta.getDescricao(),
            conta.getValor(),
            conta.getDataVencimento(),
            conta.getDataRecebimento(),
            conta.getStatus(),
            conta.getCliente().getIdCliente(),
            conta.getCliente().getNomeRazaoSocial(),
            // O pedido de venda pode ser nulo, então precisamos checar
            conta.getPedidoVenda() != null ? conta.getPedidoVenda().getIdPedidoVenda() : null
        );
    }
}