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

    // A relação com o Pedido "pai" continua a mesma
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido_venda", nullable = false)
    private PedidoVenda pedidoVenda;

    // --- MUDANÇA CRÍTICA AQUI ---
    // Removemos a ligação com Produto e agora ligamos com a Variação específica.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variacao_produto", nullable = false)
    private VariacaoProduto variacaoProduto;
    // ----------------------------

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    // O campo 'produto' foi removido, pois agora podemos acessá-lo através da variacaoProduto.getProduto()
}