package com.puc.realconsult.controller;

import com.puc.realconsult.model.realConsult.DespesaModel;
import com.puc.realconsult.model.realConsult.CategoriaDespesaModel;
import com.puc.realconsult.service.DespesaService;
import com.puc.realconsult.service.CategoriaDespesaService;
import com.puc.realconsult.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {
    
    @Autowired
    private DespesaService despesaService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CategoriaDespesaService categoriaService;
    @GetMapping
    public ResponseEntity<List<DespesaModel>> listarDespesas(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String categoriaNome,
            @RequestParam(required = false) String periodo) {

        List<DespesaModel> despesas;

        if (busca != null && !busca.trim().isEmpty()) {

            despesas = despesaService.buscarPorTermo(busca);
        } else if (categoriaNome != null && !categoriaNome.trim().isEmpty()) {

            CategoriaDespesaModel categoria = categoriaService.getCategoriaByNome(categoriaNome)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));


            despesas = despesaService.buscarPorCategoria(categoria.getNome());
        } else if (periodo != null && !periodo.trim().isEmpty()) {

            String[] datas = periodo.split(",");
            if (datas.length == 2) {
                LocalDate dataInicio = LocalDate.parse(datas[0]);
                LocalDate dataFim = LocalDate.parse(datas[1]);
                despesas = despesaService.buscarPorPeriodo(dataInicio, dataFim);
            } else {

                despesas = despesaService.listarTodas();
            }
        } else {

            despesas = despesaService.listarTodas();
        }

        return ResponseEntity.ok(despesas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DespesaModel> buscarPorId(@PathVariable Long id) {
        return despesaService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> criarDespesa(@Valid @RequestBody DespesaModel despesa) {
        try {
            DespesaModel despesaSalva = despesaService.salvar(despesa);
            String[] cargos = {"Gerente", "Administrador"};
            notificationService.enviarNotificacaoParaCargos(cargos, "Despesa " + despesaSalva.getTitulo() + " de  valor R$" + despesaSalva.getValor() + " cadastrada.");
            return ResponseEntity.ok(despesaSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("erro", "Erro ao criar despesa: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDespesa(@PathVariable Long id, @Valid @RequestBody DespesaModel despesa) {
        try {
            DespesaModel despesaAtualizada = despesaService.atualizar(id, despesa);
            return ResponseEntity.ok(despesaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("erro", "Erro ao atualizar despesa: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirDespesa(@PathVariable Long id) {
        try {
            despesaService.excluir(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("erro", "Erro ao excluir despesa: " + e.getMessage()));
        }
    }
    
    @GetMapping("/categorias")
    public ResponseEntity<List<String>> listarCategorias() {
        List<String> categorias = despesaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }
}
