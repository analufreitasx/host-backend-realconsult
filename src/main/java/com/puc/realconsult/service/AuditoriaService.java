package com.puc.realconsult.service;
import java.util.List;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.puc.realconsult.model.realConsult.AuditoriaModel;
import com.puc.realconsult.repository.realConsult.AuditoriaRepository;

@Service
@AllArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository repository;

    public List<AuditoriaModel> listarTodasAuditorias() {
        return repository.findAll();
    }

    public AuditoriaModel cadastrar(AuditoriaModel auditoria) {
        return repository.save(auditoria);
    }

    public List<AuditoriaModel> listar() {
        List<AuditoriaModel> auditorias = repository.findAll();
        if (auditorias.isEmpty()) {
            return Collections.emptyList();
        }
        return auditorias;
    }

    public List<AuditoriaModel> listarPorClienteId(Long clienteId) {
        if (clienteId == null) {
            return Collections.emptyList();
        }
        return repository.findByIdCliente(clienteId);
    }
}