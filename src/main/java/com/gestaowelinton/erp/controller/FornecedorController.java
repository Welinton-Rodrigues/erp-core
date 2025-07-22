package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.fornecedor.AtualizarFornecedorDto;
import com.gestaowelinton.erp.dto.fornecedor.CriarFornecedorDto;
import com.gestaowelinton.erp.dto.fornecedor.FornecedorResponseDto;
import com.gestaowelinton.erp.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    // Endpoint para CRIAR um novo fornecedor
    @PostMapping
    public ResponseEntity<FornecedorResponseDto> criarFornecedor( @Valid @RequestBody CriarFornecedorDto fornecedorDto) {
        FornecedorResponseDto novoFornecedor = fornecedorService.criarFornecedor(fornecedorDto);
        return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
    }

    // Endpoint para BUSCAR um fornecedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> buscarFornecedorPorId(@PathVariable Long id) {
        try {
            FornecedorResponseDto fornecedorDto = fornecedorService.buscarFornecedorPorId(id);
            return new ResponseEntity<>(fornecedorDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para LISTAR todos os fornecedores de uma empresa
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDto>> listarFornecedoresPorEmpresa(@RequestParam Long idEmpresa) {
        List<FornecedorResponseDto> fornecedores = fornecedorService.listarFornecedoresPorEmpresa(idEmpresa);
        return new ResponseEntity<>(fornecedores, HttpStatus.OK);
    }

    // Endpoint para ATUALIZAR um fornecedor
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> atualizarFornecedor(  @PathVariable Long id,@Valid @RequestBody AtualizarFornecedorDto fornecedorDto) {
        try {
            FornecedorResponseDto fornecedorAtualizado = fornecedorService.atualizarFornecedor(id, fornecedorDto);
            return new ResponseEntity<>(fornecedorAtualizado, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para DELETAR um fornecedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable Long id) {
        try {
            fornecedorService.deletarFornecedor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}