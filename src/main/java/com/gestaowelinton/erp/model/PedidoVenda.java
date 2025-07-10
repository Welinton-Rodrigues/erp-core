package com.gestaowelinton.erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido_venda")
    private Long idPedidoVenda;

    // --- Relacionamentos Fundamentais ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa; // Garante o isolamento dos dados

    // --- Dados do Pedido ---
    @Column(name = "data_pedido", nullable = false)
    private LocalDate dataPedido = LocalDate.now();

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "status", nullable = false, length = 50)
    private String status; // Ex: "ORÇAMENTO", "EMITIDO", "FATURADO", "CANCELADO"
    
    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    // --- Itens do Pedido ---
    // Um pedido tem uma lista de itens. Se o pedido for deletado, os itens vão junto.
    @OneToMany(mappedBy = "pedidoVenda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemPedidoVenda> itens;
}