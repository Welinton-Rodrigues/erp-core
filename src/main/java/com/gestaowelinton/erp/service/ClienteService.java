package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.cliente.AtualizarClienteDto;
import com.gestaowelinton.erp.dto.cliente.ClienteResponseDto;
import com.gestaowelinton.erp.dto.cliente.CriarClienteDto;
import com.gestaowelinton.erp.model.Cliente;
import com.gestaowelinton.erp.model.Empresa;
import com.gestaowelinton.erp.repository.ClienteRepository;
import com.gestaowelinton.erp.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public Cliente criarCliente(CriarClienteDto dto) {
        clienteRepository.findByCpfCnpj(dto.cpfCnpj()).ifPresent(cliente -> {
            throw new IllegalStateException("CPF ou CNPJ já cadastrado no sistema.");
        });

        Empresa empresa = empresaRepository.findById(dto.idEmpresa())
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com o ID: " + dto.idEmpresa()));

        Cliente novoCliente = new Cliente();
        novoCliente.setEmpresa(empresa);
        novoCliente.setNomeRazaoSocial(dto.nomeRazaoSocial());
        novoCliente.setCpfCnpj(dto.cpfCnpj());
        novoCliente.setTipoCliente(dto.tipoCliente());
        novoCliente.setTelefonePrincipal(dto.telefonePrincipal());
        novoCliente.setEmailPrincipal(dto.emailPrincipal());

        return clienteRepository.save(novoCliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto buscarClientePorId(Integer id) {
        return clienteRepository.findById(id)
                .map(ClienteResponseDto::new)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDto> listarClientesPorEmpresa(Long idEmpresa) {
        return clienteRepository.findByEmpresaIdEmpresa(idEmpresa)
                .stream()
                .map(ClienteResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Cliente atualizarCliente(Integer id, AtualizarClienteDto dadosAtualizados) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + id));

        clienteExistente.setNomeRazaoSocial(dadosAtualizados.nomeRazaoSocial());
        clienteExistente.setTipoCliente(dadosAtualizados.tipoCliente());
        clienteExistente.setTelefonePrincipal(dadosAtualizados.telefonePrincipal());
        clienteExistente.setEmailPrincipal(dadosAtualizados.emailPrincipal());
        
        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public void deletarCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new NoSuchElementException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}