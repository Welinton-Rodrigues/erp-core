package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.model.Cliente;
import com.gestaowelinton.erp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
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
        // --- REGRA DE NEGÓCIO 1: Validar se o CPF/CNPJ já existe ---
        Optional<Cliente> clienteExistente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpj());

        if (clienteExistente.isPresent()) {
            // Se encontrou um cliente com o mesmo CPF/CNPJ, lança uma exceção.
            throw new IllegalStateException("CPF ou CNPJ já cadastrado no sistema.");
        }

        // --- REGRA DE NEGÓCIO 2: Garantir que o cliente pertence a uma empresa ---
        // (Aqui a lógica para associar à empresa do usuário logado entraria no futuro)
        if (cliente.getEmpresa() == null) {
            throw new IllegalStateException("O cliente deve estar associado a uma empresa.");
        }

        // Se todas as regras passaram, salva o cliente no banco de dados.
        return clienteRepository.save(cliente);
    }


/**
 * Método para listar todos os clientes de uma empresa específica.
 * @param idEmpresa O ID da empresa da qual se quer listar os clientes.
 * @return Uma lista de Clientes. A lista pode estar vazia se não houver clientes.
 */
@Transactional(readOnly = true)
public List<Cliente> listarClientesPorEmpresa(Long idEmpresa) {
    // Simplesmente chama o método que já criamos no repositório.
    return clienteRepository.findByEmpresaIdEmpresa(idEmpresa);
}

    // Outros métodos virão aqui no futuro...
    /**
     * Busca um cliente pelo ID.
     * 
     * @param id O ID do cliente a ser buscado.
     * @return O Cliente encontrado.
     * @throws NoSuchElementException se o cliente não for encontrado.
     */

@Transactional(readOnly = true) // Opcional, mas boa prática para operações de leitura
public Cliente buscarClientePorId(Integer id) {
    return clienteRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + id));
}// ... dentro da classe ClienteService

// ... outros métodos ...

/**
 * Atualiza os dados de um cliente existente.
 * @param id O ID do cliente a ser atualizado.
 * @param dadosAtualizados Objeto cliente com os novos dados.
 * @return O cliente com os dados atualizados.
 * @throws NoSuchElementException se o cliente não for encontrado.
 */
@Transactional
public Cliente atualizarCliente(Integer id, Cliente dadosAtualizados) {
    // 1. Busca o cliente existente no banco de dados.
    //    Isso já lança NoSuchElementException se não encontrar, o que é perfeito.
    Cliente clienteExistente = buscarClientePorId(id);

    // 2. Atualiza os campos do cliente existente com os novos dados.
    //    (Não atualizamos o CNPJ ou a empresa, pois geralmente são imutáveis)
    clienteExistente.setNomeRazaoSocial(dadosAtualizados.getNomeRazaoSocial());
    clienteExistente.setTipoCliente(dadosAtualizados.getTipoCliente());
    clienteExistente.setTelefonePrincipal(dadosAtualizados.getTelefonePrincipal());
    clienteExistente.setEmailPrincipal(dadosAtualizados.getEmailPrincipal());
    
    // 3. Salva o cliente atualizado. O JpaRepository é inteligente e sabe
    //    que, como o objeto já tem um ID, ele deve fazer um UPDATE em vez de um INSERT.
    return clienteRepository.save(clienteExistente);
}

// ... dentro da classe ClienteService

// ... outros métodos ...

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