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
@RequestMapping("/api/contas-a-pagar")
public class ContasPagarController {

    @Autowired
    private ContasPagarService contasPagarService;
    @PostMapping
    public ResponseEntity<?> criarContaManual(@Valid @RequestBody CriarContaPagarDto dto) {
     
            ContasPagarResponseDto novaConta = contasPagarService.criarContaManual(dto); 
            return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
       
    }
}