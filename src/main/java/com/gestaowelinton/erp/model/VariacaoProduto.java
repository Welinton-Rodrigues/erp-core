package com.gestaowelinton.erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "variacoes_produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariacaoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variacao_produto")
    private Long idVariacaoProduto;

    // Relacionamento: Muitas variações pertencem a UM produto "pai".
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Column(length = 50)
    private String cor;

    @Column(length = 20)
    private String tamanho;

    @Column(name = "codigo_barras", length = 50, unique = true) 
    private String codigoBarras;

    @Column(name = "preco_venda", precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque = 0;
}