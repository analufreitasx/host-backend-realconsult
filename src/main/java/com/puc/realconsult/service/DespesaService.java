package com.puc.realconsult.service;

import com.puc.realconsult.model.realConsult.DespesaModel;
import com.puc.realconsult.repository.realConsult.DespesaRepository;
import com.puc.realconsult.service.CategoriaDespesaService;
import com.puc.realconsult.model.realConsult.CategoriaDespesaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaDespesaService categoriaService;

    public List<DespesaModel> listarTodas() {
        return despesaRepository.findAllByOrderByDataDesc();
    }

    public List<DespesaModel> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodas();
        }
        return despesaRepository.findByTermo(termo.trim());
    }

    public List<DespesaModel> buscarPorCategoria(String categoria) {

        return despesaRepository.findByCategoria_Nome(categoria);
    }

    public List<DespesaModel> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return despesaRepository.findByDataBetween(dataInicio, dataFim);
    }

    public List<DespesaModel> buscarPorCategoriaEPeriodo(String categoriaNome, LocalDate dataInicio, LocalDate dataFim) {

        CategoriaDespesaModel categoria = categoriaService.getCategoriaByNome(categoriaNome)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));


        return despesaRepository.findByCategoriaAndDataBetween(categoria, dataInicio, dataFim);
    }


    public Optional<DespesaModel> buscarPorId(Long id) {
        return despesaRepository.findById(id);
    }

    public DespesaModel salvar(DespesaModel despesa) {
        String categoriaNome = despesa.getCategoria().getNome();

        CategoriaDespesaModel categoria = categoriaService.getCategoriaByNome(categoriaNome).orElse(null);

        if (categoria == null) {
            categoria = categoriaService.criarCategoria(categoriaNome);
        }

        despesa.setCategoria(categoria);

        return despesaRepository.save(despesa);
    }

    public DespesaModel atualizar(Long id, DespesaModel despesaAtualizada) {
        DespesaModel despesaExistente = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada com ID: " + id));

        CategoriaDespesaModel categoria = categoriaService.getCategoriaByNome(despesaAtualizada.getCategoria().getNome()).orElse(null);
        if (categoria == null) {
            categoria = categoriaService.criarCategoria(despesaAtualizada.getCategoria().getNome());
        }

        despesaExistente.setTitulo(despesaAtualizada.getTitulo());
        despesaExistente.setCategoria(categoria);
        despesaExistente.setValor(despesaAtualizada.getValor());
        despesaExistente.setData(despesaAtualizada.getData());
        despesaExistente.setDescricao(despesaAtualizada.getDescricao());
        despesaExistente.setStatus(despesaAtualizada.getStatus());

        return despesaRepository.save(despesaExistente);
    }

    public void excluir(Long id) {
        if (!despesaRepository.existsById(id)) {
            throw new RuntimeException("Despesa não encontrada com ID: " + id);
        }
        despesaRepository.deleteById(id);
    }

    public List<String> listarCategorias() {
        return despesaRepository.findAll().stream()
                .map(d -> d.getCategoria().getNome())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}

