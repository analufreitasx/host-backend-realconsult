package com.puc.realconsult.service;

import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.repository.vtRealRepository.ClienteApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteApiRepository clienteApiRepository;

    private ClienteModel validarClienteExistente(Long id) {
        return clienteApiRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("❌ Cliente com ID " + id + " não encontrado."));
    }

    public int getConsultasContratadas(Long id) {
        ClienteModel cliente = validarClienteExistente(id);
        return cliente.getNumeroConsultas();
    }

    public int getConsultasRealizadas(Long id) {
        ClienteModel cliente = validarClienteExistente(id);
        return cliente.getNumeroConsultasRealizadas();
    }

    public int getSaldoConsultas(Long id) {
        ClienteModel cliente = validarClienteExistente(id);

        int contratadas = cliente.getNumeroConsultas();
        int realizadas = cliente.getNumeroConsultasRealizadas();

        if (realizadas > contratadas) {
            throw new IllegalStateException("⚠️ O número de consultas realizadas excede o contratado.");
        }

        return contratadas - realizadas;
    }

    public Iterable<ClienteModel> listarTodos() {
        return clienteApiRepository.findAll();
    }

    public ClienteModel getById(Long id) {
        return clienteApiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
    }

    public List<ClienteModel> listar() {
        List<ClienteModel> clientes = clienteApiRepository.findAll();
        if (clientes.isEmpty()) {
            return Collections.emptyList();
        }
        return clientes;
    }

    public Optional<ClienteModel> buscarPorCnpj(String cnpj) {
        return clienteApiRepository.findByCnpj(cnpj);
    }

    public Optional<ClienteModel> buscarPorId(long id) {
        return clienteApiRepository.findById(id);
    }

    public List<ClienteModel> listarPorNome(String termo) {
        List<ClienteModel> clientes = clienteApiRepository.buscarPorNomeOuCnpj(termo);
        return clientes.isEmpty() ? Collections.emptyList() : clientes;
    }

}
