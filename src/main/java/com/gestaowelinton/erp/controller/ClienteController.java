package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.model.Cliente;
import com.gestaowelinton.erp.service.ClienteService;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.gestaowelinton.erp.dto.ClienteResponseDto;
import java.util.NoSuchElementException;
import com.gestaowelinton.erp.dto.AtualizarClienteDto;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Endpoint para criar um novo cliente.
     * URL: POST http://localhost:8080/api/clientes
     * Corpo da Requisição (JSON):
     * {
     * "empresa": { "idEmpresa": 1 },
     * "nomeRazaoSocial": "Cliente de Teste SA",
     * "cpfCnpj": "12.345.678/0001-99",
     * "tipoCliente": "Pessoa Jurídica"
     * }
     * 
     * @param cliente O objeto Cliente recebido no corpo da requisição.
     * @return Uma resposta HTTP com o cliente criado e o status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.criarCliente(cliente);
            return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Se o serviço lançar nossa exceção de negócio (ex: CPF/CNPJ duplicado)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Para qualquer outro erro inesperado
            return new ResponseEntity<>("Ocorreu um erro inesperado no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * // <--- COMEÇA COM /**
     * Endpoint para buscar um cliente por ID.
     * URL: GET http://localhost:8080/api/clientes/{id}
     * 
     * @param id O ID do cliente passado na URL.
     * @return Uma resposta HTTP com o cliente encontrado (200 OK) ou um erro (404
     *         Not Found).
     */ //
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Integer id) {
        try {
            ClienteResponseDto clienteDto = clienteService.buscarClientePorId(id);
            return new ResponseEntity<>(clienteDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // Captura a exceção que o serviço lança se o cliente não for encontrado
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para listar todos os clientes de uma empresa.
     * URL: GET http://localhost:8080/api/clientes?empresaId=1
     * 
     * @param idEmpresa O ID da empresa, passado como um parâmetro na URL.
     * @return Uma resposta HTTP com a lista de clientes (200 OK).
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarClientesPorEmpresa(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        List<ClienteResponseDto> clientes = clienteService.listarClientesPorEmpresa(idEmpresa);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    /**
     * Endpoint para atualizar um cliente existente.
     * URL: PUT http://localhost:8080/api/clientes/{id}
     * 
     * @param id               O ID do cliente a ser atualizado, vindo da URL.
     * @param dadosAtualizados O JSON com os novos dados do cliente, vindo do corpo
     *                         da requisição.
     * @return Uma resposta HTTP com o cliente atualizado (200 OK) ou um erro (404
     *         Not Found).
     */
   @PutMapping("/{id}")
public ResponseEntity<ClienteResponseDto> atualizarCliente(@PathVariable Integer id, @RequestBody AtualizarClienteDto dadosAtualizados) {
    try {
        Cliente clienteAtualizadoEntidade = clienteService.atualizarCliente(id, dadosAtualizados);
        // Retornamos o DTO completo para o front-end ver o estado final do objeto
        return new ResponseEntity<>(new ClienteResponseDto(clienteAtualizadoEntidade), HttpStatus.OK);
    } catch (NoSuchElementException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    /**
     * Endpoint para deletar um cliente existente.
     * URL: DELETE http://localhost:8080/api/clientes/{id}
     * 
     * @param id O ID do cliente a ser deletado.
     * @return Uma resposta HTTP vazia com status 204 No Content (sucesso) ou um
     *         erro (404 Not Found).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
        try {
            clienteService.deletarCliente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            // Se o serviço não encontrar o cliente, retorna 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}