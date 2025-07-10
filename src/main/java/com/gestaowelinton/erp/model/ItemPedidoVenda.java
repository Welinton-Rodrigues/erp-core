package com.gestaowelinton.erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_pedido_venda")
    private Long idItemPedidoVenda;

    // Relacionamento: O item pertence a UM Pedido de Venda.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido_venda", nullable = false)
    private PedidoVenda pedidoVenda;

    // Relacionamento: O item se refere a UM Produto.
    // ATENÇÃO: A classe 'Produto' ainda não existe, vamos criá-la em seguida.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario; // Preço no momento da venda

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal; // quantidade * preco_unitario
}