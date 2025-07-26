package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.cliente.AtualizarClienteDto;
import com.gestaowelinton.erp.dto.cliente.ClienteResponseDto;
import com.gestaowelinton.erp.dto.cliente.CriarClienteDto;
import com.gestaowelinton.erp.model.Cliente;
import com.gestaowelinton.erp.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciar as operações CRUD da entidade Cliente.
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Cria um novo cliente.
     * @param clienteDto DTO com os dados para a criação do cliente.
     * @return O cliente recém-criado com status 201.
     */
    @PostMapping
    public ResponseEntity<ClienteResponseDto> criarCliente(@Valid @RequestBody CriarClienteDto clienteDto) {
        Cliente novoClienteEntidade = clienteService.criarCliente(clienteDto);
        return new ResponseEntity<>(new ClienteResponseDto(novoClienteEntidade), HttpStatus.CREATED);
    }

    /**
     * Busca um cliente específico pelo seu ID.
     * @param id O ID do cliente a ser buscado.
     * @return O cliente encontrado com status 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarClientePorId(@PathVariable Integer id) {
        ClienteResponseDto clienteDto = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(clienteDto);
    }

    /**
     * Lista todos os clientes de uma empresa específica.
     * @param idEmpresa O ID da empresa.
     * @return Uma lista com os clientes da empresa e status 200.
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarClientesPorEmpresa(@RequestParam Long idEmpresa) {
        List<ClienteResponseDto> clientes = clienteService.listarClientesPorEmpresa(idEmpresa);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Atualiza um cliente existente.
     * @param id O ID do cliente a ser atualizado.
     * @param dadosAtualizados DTO com os novos dados do cliente.
     * @return O cliente com os dados atualizados e status 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizarCliente(@PathVariable Integer id, @Valid @RequestBody AtualizarClienteDto dadosAtualizados) {
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, dadosAtualizados);
        return ResponseEntity.ok(new ClienteResponseDto(clienteAtualizado));
    }

    /**
     * Deleta um cliente pelo seu ID.
     * @param id O ID do cliente a ser deletado.
     * @return Uma resposta vazia com status 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}