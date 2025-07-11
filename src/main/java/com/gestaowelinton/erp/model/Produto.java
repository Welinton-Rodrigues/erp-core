package com.gestaowelinton.erp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    // Todo produto pertence a UMA empresa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
    
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno; // Código/SKU usado pela empresa

    @Column(name = "unidade_medida", nullable = false, length = 10)
    private String unidadeMedida; // Ex: "UN", "PC", "CX", "KG"

    @Column(name = "preco_venda", precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // "ATIVO" ou "INATIVO"

    // O controle de estoque será feito em uma entidade separada 'Estoque' no futuro
    // para suportar múltiplos locais de armazenamento.

    // Relacionamento inverso: Um produto pode estar em muitos itens de pedido
    @OneToMany(mappedBy = "produto") // Sem cascade! Não queremos deletar vendas se um produto for deletado.
    private List<ItemPedidoVenda> itensPedidoVenda;

}