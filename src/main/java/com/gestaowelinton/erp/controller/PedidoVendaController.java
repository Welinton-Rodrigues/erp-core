package com.gestaowelinton.erp.controller;

import java.util.List;
import com.gestaowelinton.erp.dto.pedido.CriarPedidoVendaRequestDto;
import com.gestaowelinton.erp.dto.pedido.PedidoVendaResponseDto;
import com.gestaowelinton.erp.model.PedidoVenda;
import com.gestaowelinton.erp.service.PedidoVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pedidos-venda") // <-- Garanta que está no plural
public class PedidoVendaController {

    @Autowired
    private PedidoVendaService pedidoVendaService;

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody CriarPedidoVendaRequestDto pedidoDto) {
        try {
            PedidoVenda novoPedido = pedidoVendaService.criarPedido(pedidoDto);
            // No futuro, criaremos um DTO de resposta para o pedido também.
            // Por enquanto, retornar a entidade ajuda no debug.
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (NoSuchElementException | IllegalStateException e) {
            // Captura erros de "item não encontrado" ou "sem estoque"
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Captura qualquer outro erro inesperado
            return new ResponseEntity<>("Ocorreu um erro inesperado ao processar o pedido.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoVendaResponseDto> buscarPedidoPorId(@PathVariable Long id) {
        try {
            PedidoVendaResponseDto pedidoDto = pedidoVendaService.buscarPedidoPorId(id);
            return new ResponseEntity<>(pedidoDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --- Endpoint para LISTAR todos os pedidos de uma empresa ---
    @GetMapping
    public ResponseEntity<List<PedidoVendaResponseDto>> listarPedidosPorEmpresa(@RequestParam Long idEmpresa) {
        List<PedidoVendaResponseDto> pedidos = pedidoVendaService.listarPedidosPorEmpresa(idEmpresa);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }


    // --- Endpoint para CANCELAR um pedido ---
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        try {
            PedidoVendaResponseDto pedidoCancelado = pedidoVendaService.cancelarPedido(id);
            return new ResponseEntity<>(pedidoCancelado, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // Se o pedido com o ID não existe
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            // Se a regra de negócio impedir o cancelamento (ex: status inválido)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}