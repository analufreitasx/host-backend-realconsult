package com.puc.realconsult.service;

import com.puc.realconsult.dto.LembreteClienteDTO;
import com.puc.realconsult.model.realConsult.LembreteModel;
import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.repository.realConsult.LembreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LembreteService {

    private final LembreteRepository lembreteRepository;
    private final ClienteService clienteService;

    public List<LembreteModel> listarTodos() {
        return lembreteRepository.findAll();
    }

    public List<LembreteModel> buscarPorCliente(Long clienteId) {
        return lembreteRepository.findByIdCliente(clienteId);
    }

    public Optional<LembreteModel> buscarPorId(Long id) {
        return lembreteRepository.findById(id);
    }

    public LembreteModel salvar(LembreteModel lembrete) {
        return lembreteRepository.save(lembrete);
    }

    public LembreteModel atualizar(Long id, LembreteModel dados) {
        LembreteModel existente = lembreteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lembrete não encontrado"));

        existente.setIdCliente(dados.getIdCliente());
        existente.setDataHorario(dados.getDataHorario());
        return lembreteRepository.save(existente);
    }

    public void excluir(Long id) {
        if (!lembreteRepository.existsById(id)) {
            throw new RuntimeException("Lembrete não encontrado");
        }
        lembreteRepository.deleteById(id);
    }

    private LembreteClienteDTO toDTO(LembreteModel lembrete) {
        String nomeEmpresa = null;
        String cnpj = null;

        if (lembrete.getIdCliente() != null) {
            try {
                ClienteModel cliente = clienteService.getById(lembrete.getIdCliente());
                if (cliente != null) {
                    nomeEmpresa = cliente.getNomeEmpresa();
                    cnpj = cliente.getCnpj();
                }
            } catch (Exception ignored) {
            }
        }

        return new LembreteClienteDTO(
                lembrete.getId(),
                lembrete.getIdCliente(),
                nomeEmpresa,
                cnpj,
                lembrete.getDataHorario(),
                lembrete.getDataCriacao()
        );
    }

    public List<LembreteClienteDTO> listarDTO(Long clienteId) {
        List<LembreteModel> lembretes = (clienteId != null)
                ? buscarPorCliente(clienteId)
                : listarTodos();

        return lembretes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<LembreteClienteDTO> buscarDTOporId(Long id) {
        return buscarPorId(id).map(this::toDTO);
    }
}
