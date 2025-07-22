package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.produto.CriarProdutoDto;
import com.gestaowelinton.erp.dto.produto.ProdutoResponseDto;
// Remova os imports dos DTOs de atualização antigos
import com.gestaowelinton.erp.service.ProdutoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // --- Endpoint de CRIAÇÃO (Totalmente Refatorado) ---
    @PostMapping
    public ResponseEntity<ProdutoResponseDto> criarProduto(@Valid @RequestBody CriarProdutoDto produtoDto) {
        ProdutoResponseDto novoProduto = produtoService.criarProduto(produtoDto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    // --- Endpoints de LEITURA (Assinaturas Atualizadas) ---
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarProdutoPorId(@PathVariable Long id) {
        try {
            ProdutoResponseDto produtoDto = produtoService.buscarProdutoPorId(id);
            return new ResponseEntity<>(produtoDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listarProdutosPorEmpresa(@RequestParam Long idEmpresa) {
        List<ProdutoResponseDto> produtos = produtoService.listarProdutosPorEmpresa(idEmpresa);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    // Dentro da classe ProdutoController.java



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