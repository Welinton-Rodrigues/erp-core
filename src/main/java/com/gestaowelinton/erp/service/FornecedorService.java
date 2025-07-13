package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.AtualizarFornecedorDto;
import com.gestaowelinton.erp.dto.CriarFornecedorDto;
import com.gestaowelinton.erp.dto.FornecedorResponseDto;
import com.gestaowelinton.erp.model.Empresa;
import com.gestaowelinton.erp.model.Fornecedor;
import com.gestaowelinton.erp.repository.EmpresaRepository;
import com.gestaowelinton.erp.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private EmpresaRepository empresaRepository; // Injetamos o novo repositório

    @Transactional
    public FornecedorResponseDto criarFornecedor(CriarFornecedorDto dto) {
        // 1. Busca a entidade Empresa para fazer a associação
        Empresa empresa = empresaRepository.findById(dto.idEmpresa())
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com o ID: " + dto.idEmpresa()));

        // 2. Cria a nova entidade Fornecedor a partir dos dados do DTO
        Fornecedor novoFornecedor = new Fornecedor();
        novoFornecedor.setEmpresa(empresa);
        novoFornecedor.setNomeRazaoSocial(dto.nomeRazaoSocial());
        novoFornecedor.setCnpj(dto.cnpj());
        novoFornecedor.setTelefone(dto.telefone());
        novoFornecedor.setEmail(dto.email());

        // 3. Salva a nova entidade no banco
        Fornecedor fornecedorSalvo = fornecedorRepository.save(novoFornecedor);

        // 4. Converte a entidade salva para o DTO de resposta e retorna
        return new FornecedorResponseDto(fornecedorSalvo);
    }

    @Transactional(readOnly = true)
    public FornecedorResponseDto buscarFornecedorPorId(Long id) {
        return fornecedorRepository.findById(id)
                .map(FornecedorResponseDto::new) // Converte a entidade para DTO
                .orElseThrow(() -> new NoSuchElementException("Fornecedor não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<FornecedorResponseDto> listarFornecedoresPorEmpresa(Long idEmpresa) {
        return fornecedorRepository.findByEmpresaIdEmpresa(idEmpresa)
                .stream()
                .map(FornecedorResponseDto::new) // Converte cada entidade da lista para DTO
                .collect(Collectors.toList());
    }

    @Transactional
    public FornecedorResponseDto atualizarFornecedor(Long id, AtualizarFornecedorDto dto) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fornecedor não encontrado com o ID: " + id));

        fornecedorExistente.setNomeRazaoSocial(dto.nomeRazaoSocial());
        fornecedorExistente.setTelefone(dto.telefone());
        fornecedorExistente.setEmail(dto.email());

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedorExistente);
        return new FornecedorResponseDto(fornecedorSalvo);
    }

    @Transactional
    public void deletarFornecedor(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new NoSuchElementException("Fornecedor não encontrado com o ID: " + id);
        }
        fornecedorRepository.deleteById(id);
    }
}