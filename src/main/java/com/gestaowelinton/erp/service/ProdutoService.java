package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.produto.CriarProdutoDto;
import com.gestaowelinton.erp.dto.produto.ProdutoResponseDto;
import com.gestaowelinton.erp.dto.produto.VariacaoProdutoDto;
import com.gestaowelinton.erp.model.Empresa;
import com.gestaowelinton.erp.model.Produto;
import com.gestaowelinton.erp.model.VariacaoProduto;
import com.gestaowelinton.erp.repository.EmpresaRepository;
import com.gestaowelinton.erp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public ProdutoResponseDto criarProduto(CriarProdutoDto dto) {
        // 1. Busca a entidade Empresa para fazer a associação.
        Empresa empresa = empresaRepository.findById(dto.idEmpresa())
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com o ID: " + dto.idEmpresa()));

        // 2. Cria a entidade "pai" Produto.
        Produto produtoPai = new Produto();
        produtoPai.setEmpresa(empresa);
        produtoPai.setNome(dto.nome());
        produtoPai.setCodigoInterno(dto.codigoInterno());
        produtoPai.setUnidadeMedida(dto.unidadeMedida());
        produtoPai.setStatus(dto.status());
        // A lista de variações começa vazia
        produtoPai.setVariacoes(new ArrayList<>());

        // 3. Itera sobre os DTOs de variação para criar as entidades "filhas".
        for (VariacaoProdutoDto variacaoDto : dto.variacoes()) {
            VariacaoProduto novaVariacao = new VariacaoProduto();
            novaVariacao.setCor(variacaoDto.cor());
            novaVariacao.setTamanho(variacaoDto.tamanho());
            novaVariacao.setCodigoBarras(variacaoDto.codigoBarras());
            novaVariacao.setPrecoVenda(variacaoDto.precoVenda());
            novaVariacao.setQuantidadeEstoque(variacaoDto.quantidadeEstoque());
            
            novaVariacao.setProduto(produtoPai);

            // 5. Adiciona a variação recém-criada na lista do produto pai.
            produtoPai.getVariacoes().add(novaVariacao);
        }

        Produto produtoSalvo = produtoRepository.save(produtoPai);

        // 7. Converte a entidade salva (com todos os IDs gerados) para o DTO de resposta.
        return new ProdutoResponseDto(produtoSalvo);
    }
    
    @Transactional(readOnly = true)
    public ProdutoResponseDto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoResponseDto::new)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDto> listarProdutosPorEmpresa(Long idEmpresa) {
        return produtoRepository.findAll()
                .stream()
                .filter(p -> p.getEmpresa().getIdEmpresa().equals(idEmpresa))
                .map(ProdutoResponseDto::new)
                .collect(Collectors.toList());
    }
@Transactional
public void deletarProduto(Long id) {
    // 1. Apenas verificamos se o produto existe para poder retornar um erro 404 caso não exista.
    if (!produtoRepository.existsById(id)) {
        throw new NoSuchElementException("Produto não encontrado com o ID: " + id);
    }
    
    // 2. O JpaRepository cuida de deletar o produto "pai".
    //    O CascadeType.ALL se encarrega de deletar todas as "VariacaoProduto" filhas automaticamente.
    produtoRepository.deleteById(id);
}



  
}