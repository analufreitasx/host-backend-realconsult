package com.puc.realconsult.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.puc.realconsult.model.realConsult.CategoriaDespesaModel;
import com.puc.realconsult.repository.realConsult.CategoriaDespesaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaDespesaService {

    @Autowired
    private CategoriaDespesaRepository categoriaRepository;

    public List<CategoriaDespesaModel> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<CategoriaDespesaModel> getCategoriaByNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }


    public CategoriaDespesaModel criarCategoria(String nome) {
        CategoriaDespesaModel novaCategoria = new CategoriaDespesaModel();
        novaCategoria.setNome(nome);
        return categoriaRepository.save(novaCategoria);
    }
}
