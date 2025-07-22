package com.gestaowelinton.erp.dto.financeiro;

import com.gestaowelinton.erp.model.ContasPagar;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContasPagarResponseDto(
    Long idContaPagar,
    String descricao,
    BigDecimal valor,
    LocalDate dataVencimento,
    LocalDate dataPagamento,
    String status,
    Long idFornecedor,
    String nomeFornecedor,
    Long idPedidoCompra
) {
    public ContasPagarResponseDto(ContasPagar conta) {
        this(
            conta.getIdContaPagar(),
            conta.getDescricao(),
            conta.getValor(),
            conta.getDataVencimento(),
            conta.getDataPagamento(),
            conta.getStatus(),
            conta.getFornecedor() != null ? conta.getFornecedor().getIdFornecedor() : null,
            conta.getFornecedor() != null ? conta.getFornecedor().getNomeRazaoSocial() : null,
            conta.getPedidoCompra() != null ? conta.getPedidoCompra().getIdPedidoCompra() : null
        );
    }
}