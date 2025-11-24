package com.puc.realconsult.controller;

import com.puc.realconsult.dto.LembreteClienteDTO;
import com.puc.realconsult.model.realConsult.LembreteModel;
import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.service.ClienteService;
import com.puc.realconsult.service.LembreteService;
import com.puc.realconsult.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lembretes")
public class LembreteController {

    @Autowired
    private LembreteService lembreteService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<LembreteClienteDTO>> listarLembretes(
            @RequestParam(required = false) Long clienteId) {

        List<LembreteClienteDTO> dtos = lembreteService.listarDTO(clienteId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LembreteClienteDTO> buscarPorId(@PathVariable Long id) {
        return lembreteService.buscarDTOporId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarLembrete(@Valid @RequestBody LembreteModel lembrete) {
        try {
            if (lembrete.getDataHorario() != null &&
                    lembrete.getDataHorario().isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro",
                                "Não é possível cadastrar lembrete com data/horário no passado"));
            }

            LembreteModel lembreteSalvo = lembreteService.salvar(lembrete);
            String[] cargos = {"Gerente", "Administrador"};

            String nomeCliente = "Cliente não encontrado";
            if (lembreteSalvo.getIdCliente() != null) {
                ClienteModel cliente = clienteService.getById(lembreteSalvo.getIdCliente());
                if (cliente.getNomeEmpresa() != null) {
                    nomeCliente = cliente.getNomeEmpresa();
                }
            }

            notificationService.enviarNotificacaoParaCargos(
                    cargos,
                    "Lembrete NF criado para o cliente " + nomeCliente +
                            " em " + lembreteSalvo.getDataHorario().toLocalDate() +
                            " às " + lembreteSalvo.getDataHorario().toLocalTime()
            );

            return ResponseEntity.ok(
                    lembreteService.buscarDTOporId(lembreteSalvo.getId()).orElseThrow()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Erro ao criar lembrete: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarLembrete(@PathVariable Long id,
                                               @Valid @RequestBody LembreteModel lembrete) {
        try {
            if (lembrete.getDataHorario() != null &&
                    lembrete.getDataHorario().isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro",
                                "Não é possível atualizar lembrete com data/horário no passado"));
            }

            LembreteModel lembreteAtualizado = lembreteService.atualizar(id, lembrete);
            return ResponseEntity.ok(
                    lembreteService.buscarDTOporId(lembreteAtualizado.getId()).orElseThrow()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Erro ao atualizar lembrete: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirLembrete(@PathVariable Long id) {
        try {
            lembreteService.excluir(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Erro ao excluir lembrete: " + e.getMessage()));
        }
    }
}
