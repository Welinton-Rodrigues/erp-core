package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.ProdutoDto;
import com.gestaowelinton.erp.model.Produto;
import com.gestaowelinton.erp.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import com.gestaowelinton.erp.dto.ProdutoDto; 
import com.gestaowelinton.erp.dto.AtualizarProdutoDto;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // 1. Endpoint para CRIAR um novo produto
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.criarProduto(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    // 2. Endpoint para BUSCAR um produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        try {
            ProdutoDto produto = produtoService.buscarProdutoPorId(id);
            return new ResponseEntity<>(produto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 3. Endpoint para LISTAR todos os produtos de uma empresa
    @GetMapping
    public ResponseEntity<List<ProdutoDto>> listarProdutosPorEmpresa(@RequestParam Long idEmpresa) {
        List<ProdutoDto> produtos = produtoService.listarProdutosPorEmpresa(idEmpresa);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    // 4. Endpoint para ATUALIZAR um produto
// No ProdutoController.java
@PutMapping("/{id}")
public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody AtualizarProdutoDto produtoDto) {
    try {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoDto);
        // Podemos retornar a entidade atualizada ou o DTO dela
        return new ResponseEntity<>(new ProdutoDto(produtoAtualizado), HttpStatus.OK);
    } catch (NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

    // 5. Endpoint para DELETAR um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletarProduto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}