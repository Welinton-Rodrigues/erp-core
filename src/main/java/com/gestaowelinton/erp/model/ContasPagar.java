package com.gestaowelinton.erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas_a_pagar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContasPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta_pagar")
    private Long idContaPagar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    // Uma conta a pagar pode estar ligada a um fornecedor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
    
    // Uma conta a pagar pode ter se originado de um pedido de compra
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido_compra")
    private PedidoCompra pedidoCompra;

    @Column(nullable = false, length = 255)
    private String descricao; // Ex: "Compra de matéria-prima", "Aluguel", "Salário"

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento") // Nulo até que a conta seja paga
    private LocalDate dataPagamento;

    @Column(nullable = false, length = 50)
    private String status; // Ex: "A PAGAR", "PAGO", "VENCIDO", "CANCELADO"
}