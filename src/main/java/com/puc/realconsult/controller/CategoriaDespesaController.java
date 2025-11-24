package com.puc.realconsult.controller;

import com.puc.realconsult.service.CategoriaDespesaService;
import com.puc.realconsult.model.realConsult.CategoriaDespesaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaDespesaController {

    @Autowired
    private CategoriaDespesaService categoriaService;

    @GetMapping
    public List<CategoriaDespesaModel> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> getCategoriaByNome(@PathVariable String nome) {

        Optional<CategoriaDespesaModel> categoria = categoriaService.getCategoriaByNome(nome);


        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        }


        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Categoria não encontrada com o nome: " + nome);
    }

    @PostMapping
    public CategoriaDespesaModel criarCategoria(@RequestBody CategoriaDespesaModel categoria) {
        return categoriaService.criarCategoria(categoria.getNome());
    }
}
