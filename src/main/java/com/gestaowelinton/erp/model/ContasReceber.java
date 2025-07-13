package com.gestaowelinton.erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas_receber")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContasReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta_receber")
    private Long idContaReceber;

    // --- Relacionamentos para Rastreabilidade e Segurança ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa; // A qual empresa pertence esta conta

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente; // Quem é o devedor

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido_venda") 
    private PedidoVenda pedidoVenda; // De qual venda originou esta conta

    // --- Dados Financeiros da Conta ---
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao; // Ex: "Parcela 1/3 da Venda #123"

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_recebimento") // Nulo até que a conta seja paga
    private LocalDate dataRecebimento;

    @Column(name = "status", nullable = false, length = 50)
    private String status; // Ex: "A VENCER", "PAGO", "VENCIDO", "CANCELADO"

}