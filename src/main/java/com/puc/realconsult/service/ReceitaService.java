package com.puc.realconsult.service;

import com.puc.realconsult.model.realConsult.ReceitaModel;
import com.puc.realconsult.repository.realConsult.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public List<ReceitaModel> listarTodas() {
        return receitaRepository.findAllByOrderByDataDesc();
    }

    public List<ReceitaModel> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodas();
        }
        return receitaRepository.findByTermo(termo.trim());
    }

    public List<ReceitaModel> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return receitaRepository.findByDataBetween(dataInicio, dataFim);
    }

    public Optional<ReceitaModel> buscarPorId(Long id) {
        return receitaRepository.findById(id);
    }

    public ReceitaModel salvar(ReceitaModel receita) {
        return receitaRepository.save(receita);
    }

    public ReceitaModel atualizar(Long id, ReceitaModel receitaAtualizada) {
        ReceitaModel receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada com ID: " + id));

        receitaExistente.setTitulo(receitaAtualizada.getTitulo());
        receitaExistente.setValor(receitaAtualizada.getValor());
        receitaExistente.setData(receitaAtualizada.getData());
        receitaExistente.setDescricao(receitaAtualizada.getDescricao());
        receitaExistente.setStatus(receitaAtualizada.getStatus());

        return receitaRepository.save(receitaExistente);
    }

    public void excluir(Long id) {
        if (!receitaRepository.existsById(id)) {
            throw new RuntimeException("Receita não encontrada com ID: " + id);
        }
        receitaRepository.deleteById(id);
    }
}

