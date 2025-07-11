package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.model.Produto;
import com.gestaowelinton.erp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import com.gestaowelinton.erp.dto.ProdutoDto;
import com.gestaowelinton.erp.dto.AtualizarProdutoDto;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Cria um novo produto.
     * @param produto O produto a ser criado.
     * @return O produto salvo.
     */
    @Transactional
    public Produto criarProduto(Produto produto) {
        // Lógica de validação (ex: verificar código interno duplicado) pode ser adicionada aqui no futuro.
        return produtoRepository.save(produto);
    }

    /**
     * Busca um produto pelo seu ID.
     * @param id O ID do produto.
     * @return O produto encontrado.
     * @throws NoSuchElementException se o produto não for encontrado.
     */
    @Transactional(readOnly = true)
    public ProdutoDto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
        .map(ProdutoDto::new)
        .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o ID: " + id));
    }


    /**
     * Lista todos os produtos de uma empresa.
     * @param idEmpresa O ID da empresa.
     * @return Uma lista de produtos.
     */
    @Transactional(readOnly = true)
    public List<ProdutoDto> listarProdutosPorEmpresa(Long idEmpresa) {
        return produtoRepository.findByEmpresaIdEmpresa(idEmpresa).stream().map(ProdutoDto::new).toList();
    }

    /**
     * Atualiza um produto existente.
     * @param id O ID do produto a ser atualizado.
     * @param dadosAtualizados Os novos dados do produto.
     * @return O produto atualizado.
     */
   // Dentro da classe ProdutoService.java

@Transactional
public Produto atualizarProduto(Long id, AtualizarProdutoDto dadosAtualizados) {
    Produto produtoExistente = produtoRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o ID: " + id));

    // Atualiza a entidade com os dados que vêm do DTO
    // Note que para 'record', acessamos os campos como se fossem métodos: .nome()
    produtoExistente.setNome(dadosAtualizados.nome());
    produtoExistente.setCodigoInterno(dadosAtualizados.codigoInterno());
    produtoExistente.setUnidadeMedida(dadosAtualizados.unidadeMedida());
    produtoExistente.setPrecoVenda(dadosAtualizados.precoVenda());
    produtoExistente.setStatus(dadosAtualizados.status());

    return produtoRepository.save(produtoExistente);
}
    /**
     * Deleta um produto pelo seu ID.
     * @param id O ID do produto a ser deletado.
     */
    @Transactional
    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}