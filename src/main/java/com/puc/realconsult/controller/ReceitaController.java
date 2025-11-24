package com.puc.realconsult.controller;

import com.puc.realconsult.model.realConsult.ReceitaModel;
import com.puc.realconsult.service.ReceitaService;
import com.puc.realconsult.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<ReceitaModel>> listarReceitas(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String periodo) {

        List<ReceitaModel> receitas;

        if (busca != null && !busca.trim().isEmpty()) {
            receitas = receitaService.buscarPorTermo(busca);
        } else if (periodo != null && !periodo.trim().isEmpty()) {
            String[] datas = periodo.split(",");
            if (datas.length == 2) {
                LocalDate dataInicio = LocalDate.parse(datas[0]);
                LocalDate dataFim = LocalDate.parse(datas[1]);
                receitas = receitaService.buscarPorPeriodo(dataInicio, dataFim);
            } else {
                receitas = receitaService.listarTodas();
            }
        } else {
            receitas = receitaService.listarTodas();
        }

        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaModel> buscarPorId(@PathVariable Long id) {
        return receitaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarReceita(@Valid @RequestBody ReceitaModel receita) {
        try {
            ReceitaModel receitaSalva = receitaService.salvar(receita);
            String[] cargos = {"Gerente", "Administrador"};
            notificationService.enviarNotificacaoParaCargos(cargos, "Receita " + receitaSalva.getTitulo() + " de  valor R$" + receitaSalva.getValor() + " cadastrada.");
            return ResponseEntity.ok(receitaSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Erro ao criar receita: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarReceita(@PathVariable Long id, @Valid @RequestBody ReceitaModel receita) {
        try {
            ReceitaModel receitaAtualizada = receitaService.atualizar(id, receita);
            return ResponseEntity.ok(receitaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Erro ao atualizar receita: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirReceita(@PathVariable Long id) {
        try {
            receitaService.excluir(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Erro ao excluir receita: " + e.getMessage()));
        }
    }
}
