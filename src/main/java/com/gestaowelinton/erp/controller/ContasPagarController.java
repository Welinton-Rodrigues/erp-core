package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.financeiro.ContasPagarResponseDto;

import com.gestaowelinton.erp.dto.financeiro.CriarContaPagarDto;
import com.gestaowelinton.erp.service.ContasPagarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
@RestController
@RequestMapping("/api/contas-a-pagar") // A URL pode continuar a mesma, é uma boa prática
public class ContasPagarController {

    @Autowired
    private ContasPagarService contasPagarService; // <-- Corrigido

    @PostMapping
    public ResponseEntity<?> criarContaManual(@Valid @RequestBody CriarContaPagarDto dto) {
        try {
            ContasPagarResponseDto novaConta = contasPagarService.criarContaManual(dto); // <-- Corrigido
            return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}