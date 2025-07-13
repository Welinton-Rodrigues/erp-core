package com.gestaowelinton.erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long idProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
    
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno; // Código "pai" do produto

    @Column(name = "unidade_medida", nullable = false, length = 10)
    private String unidadeMedida; // Ex: "UN", "PC", "CX"

    @Column(name = "status", nullable = false, length = 20)
    private String status; // "ATIVO" ou "INATIVO"

    // --- CAMPOS REMOVIDOS ---
    // O 'precoVenda' e 'quantidadeEstoque' foram movidos para a entidade VariacaoProduto.

    // --- NOVA RELAÇÃO ---
    // Um Produto agora tem uma lista de Variações.
    // CascadeType.ALL: Se um produto for deletado, suas variações vão junto.
    // orphanRemoval = true: Se uma variação for removida da lista, ela é deletada do banco.
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VariacaoProduto> variacoes;

    // A relação com ItemPedidoVenda continua, mas precisaremos ajustá-la depois.
   // @OneToMany(mappedBy = "produto")
   // private List<ItemPedidoVenda> itensPedidoVenda;
}