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
            // O fornecedor pode ser nulo, então precisamos checar
            conta.getFornecedor() != null ? conta.getFornecedor().getIdFornecedor() : null,
            conta.getFornecedor() != null ? conta.getFornecedor().getNomeRazaoSocial() : null,
            // O pedido de compra também pode ser nulo
            conta.getPedidoCompra() != null ? conta.getPedidoCompra().getIdPedidoCompra() : null
        );
    }
}