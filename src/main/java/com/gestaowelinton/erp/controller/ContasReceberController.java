package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.financeiro.ContasReceberResponseDto;
import com.gestaowelinton.erp.service.ContasReceberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/contas-a-receber")
public class ContasReceberController {

    @Autowired
    private ContasReceberService contasReceberService;

    @PutMapping("/{id}/quitar")
    public ResponseEntity<?> quitarConta(@PathVariable Long id) {
        try {
            ContasReceberResponseDto contaPaga = contasReceberService.quitarConta(id);
            return new ResponseEntity<>(contaPaga, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}