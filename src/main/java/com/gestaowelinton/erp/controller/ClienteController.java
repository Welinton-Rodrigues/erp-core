package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.cliente.AtualizarClienteDto;
import com.gestaowelinton.erp.dto.cliente.ClienteResponseDto;
import com.gestaowelinton.erp.dto.cliente.CriarClienteDto;
import com.gestaowelinton.erp.model.Cliente;
import com.gestaowelinton.erp.service.ClienteService;

import jakarta.validation.Valid;

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
import java.util.NoSuchElementException;
import com.gestaowelinton.erp.dto.cliente.CriarClienteDto;
import com.gestaowelinton.erp.dto.cliente.ClienteResponseDto;
import com.gestaowelinton.erp.dto.cliente.CriarClienteDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * @param cliente O objeto Cliente recebido no corpo da requisição.
     * @return Uma resposta HTTP com o cliente criado e o status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<?> criarCliente(@Valid @RequestBody CriarClienteDto clienteDto) {
        Cliente novoClienteEntidade = clienteService.criarCliente(clienteDto);
        return new ResponseEntity<>(new ClienteResponseDto(novoClienteEntidade), HttpStatus.CREATED);
    }

    /**
     * 
     * @param id
     * @return
     */ //
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Integer id) {
        ClienteResponseDto clienteDto = clienteService.buscarClientePorId(id);
        return new ResponseEntity<>(clienteDto, HttpStatus.OK);
    }

    /**
     * 
     * @param idEmpresa O ID da empresa, passado como um parâmetro na URL.
     * @return Uma resposta HTTP com a lista de clientes (200 OK).
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarClientesPorEmpresa(
            @RequestParam(name = "idEmpresa") Long idEmpresa) {
        List<ClienteResponseDto> clientes = clienteService.listarClientesPorEmpresa(idEmpresa);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    /**
     * 
     * @param id               O ID do cliente a ser atualizado, vindo da URL.
     * @param dadosAtualizados O JSON com os novos dados do cliente, vindo do corpo
     *                         da requisição.
     * @return Uma resposta HTTP com o cliente atualizado (200 OK) ou um erro (404
     *         Not Found).
     */

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable Integer id,
            @Valid @RequestBody AtualizarClienteDto dadosAtualizados) {

        Cliente clienteAtualizadoEntidade = clienteService.atualizarCliente(id, dadosAtualizados);
        ClienteResponseDto responseDto = new ClienteResponseDto(clienteAtualizadoEntidade);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    /**
     * @param id O ID do cliente a ser deletado.
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {

        clienteService.deletarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}