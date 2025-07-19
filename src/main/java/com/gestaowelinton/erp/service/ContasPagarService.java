package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.financeiro.ContasPagarResponseDto; // <-- Corrigido
import com.gestaowelinton.erp.dto.financeiro.CriarContaPagarDto;
import com.gestaowelinton.erp.model.ContasPagar; // <-- Corrigido
import com.gestaowelinton.erp.model.Empresa;
import com.gestaowelinton.erp.model.Fornecedor;
import com.gestaowelinton.erp.repository.ContasPagarRepository; // <-- Corrigido
import com.gestaowelinton.erp.repository.EmpresaRepository;
import com.gestaowelinton.erp.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ContasPagarService {

    @Autowired
    private ContasPagarRepository contasPagarRepository; // <-- Corrigido

    // ... (injeção dos outros repositórios) ...

    @Transactional
    public ContasPagarResponseDto criarContaManual(CriarContaPagarDto dto) {
        // ... (lógica interna) ...
        ContasPagar novaConta = new ContasPagar(); // <-- Corrigido
        // ...
        ContasPagar contaSalva = contasPagarRepository.save(novaConta); // <-- Corrigido
        return new ContasPagarResponseDto(contaSalva); // <-- Corrigido
    }
}