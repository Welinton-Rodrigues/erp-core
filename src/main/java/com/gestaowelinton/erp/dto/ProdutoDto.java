package com.gestaowelinton.erp.dto;
import com.gestaowelinton.erp.model.Produto;
import java.math.BigDecimal;

public record ProdutoDto(
    Long idProduto,
    String nome,
    String codigoInterno,
    String unidadeMedida,
    BigDecimal precoVenda,
    String status) {

         public ProdutoDto(Produto produto) {
        this(
            produto.getIdProduto(),
            produto.getNome(),
            produto.getCodigoInterno(),
            produto.getUnidadeMedida(),
            produto.getPrecoVenda(),
            produto.getStatus()
        );
    }
    
}
