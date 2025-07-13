package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.ClienteDto.AtualizarClienteDto;
import com.gestaowelinton.erp.dto.ClienteDto.ClienteResponseDto;
import com.gestaowelinton.erp.model.Cliente;
import com.gestaowelinton.erp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ClienteService {

    // Injeção de dependência: O Spring nos dará uma instância do ClienteRepository
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Método para criar um novo cliente com validações de negócio.
     * 
     * @param cliente O objeto Cliente a ser salvo.
     * @return O Cliente salvo com o ID gerado.
     * @throws IllegalStateException se o CPF/CNPJ já estiver em uso.
     */
    @Transactional
    public Cliente criarCliente(Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpj());

        if (clienteExistente.isPresent()) {
            throw new IllegalStateException("CPF ou CNPJ já cadastrado no sistema.");
        }

        // --- REGRA DE NEGÓCIO 2: Garantir que o cliente pertence a uma empresa ---
        if (cliente.getEmpresa() == null) {
            throw new IllegalStateException("O cliente deve estar associado a uma empresa.");
        }

        // Se todas as regras passaram, salva o cliente no banco de dados.
        return clienteRepository.save(cliente);
    }


/**
 * @param idEmpresa O ID da empresa da qual se quer listar os clientes.
 * @return Uma lista de Clientes. A lista pode estar vazia se não houver clientes.
 */
@Transactional(readOnly = true)
public List<ClienteResponseDto> listarClientesPorEmpresa(Long idEmpresa) {
    return clienteRepository.findByEmpresaIdEmpresa(idEmpresa)
            .stream()
            .map(ClienteResponseDto::new)
            .collect(Collectors.toList());
}

    /**
     * Busca um cliente pelo ID.
     * 
     * @param id O ID do cliente a ser buscado.
     * @return O Cliente encontrado.
     * @throws NoSuchElementException se o cliente não for encontrado.
     */

@Transactional(readOnly = true) // Opcional, mas boa prática para operações de leitura
public ClienteResponseDto buscarClientePorId(Integer id) {
    return clienteRepository.findById(id)
            .map(ClienteResponseDto::new) // Converte a entidade encontrada para o DTO
            .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + id));
}

/**
 * Atualiza os dados de um cliente existente.
 * @param id O ID do cliente a ser atualizado.
 * @param dadosAtualizados Objeto cliente com os novos dados.
 * @return O cliente com os dados atualizados.
 * @throws NoSuchElementException se o cliente não for encontrado.
 */

@Transactional
public Cliente atualizarCliente(Integer id, AtualizarClienteDto dadosAtualizados) {
    Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + id));

    // Atualiza a entidade com os dados do DTO
    clienteExistente.setNomeRazaoSocial(dadosAtualizados.nomeRazaoSocial());
    clienteExistente.setTipoCliente(dadosAtualizados.tipoCliente());
    clienteExistente.setTelefonePrincipal(dadosAtualizados.telefonePrincipal());
    clienteExistente.setEmailPrincipal(dadosAtualizados.emailPrincipal());
    
    // Salva a entidade atualizada
    return clienteRepository.save(clienteExistente);
}



/**
 * Deleta um cliente pelo seu ID.
 * @param id O ID do cliente a ser deletado.
 * @throws NoSuchElementException se o cliente não for encontrado.
 */
@Transactional
public void deletarCliente(Integer id) {
    // 1. Verifica se o cliente existe antes de tentar deletar.
    //    Se não existir, o findById já lança a exceção para nós.
    if (!clienteRepository.existsById(id)) {
        throw new NoSuchElementException("Cliente não encontrado com o ID: " + id);
    }
    
    // 2. Se o cliente existe, deleta.
    clienteRepository.deleteById(id);
}

    // public List<Cliente> listarClientesPorEmpresa(Long idEmpresa) { ... }
    // public Cliente atualizarCliente(Integer id, Cliente cliente) { ... }
    // public void deletarCliente(Integer id) { ... }
}