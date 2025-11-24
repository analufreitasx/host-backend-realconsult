package com.puc.realconsult.controller;

import com.puc.realconsult.model.realConsult.AuditoriaModel;
import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.repository.vtRealRepository.ClienteApiRepository;
import com.puc.realconsult.service.AuditoriaService;
import com.puc.realconsult.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ClienteApiRepository clienteRepository;
    @Autowired
    private AuditoriaService auditoriaService;

    @PostMapping
    public ResponseEntity<AuditoriaModel> criarAuditoria(@Valid @RequestBody AuditoriaModel auditoria){
        auditoriaService.cadastrar(auditoria);
        String[] cargos = {"Analista", "Gerente", "Administrador"};
        ClienteModel cliente = clienteRepository.findById(auditoria.getIdCliente())
                .orElse(null);
        notificationService.enviarNotificacaoParaCargos(cargos, "Auditoria " + auditoria.getNome() + " criada no cliente " + cliente.getNomeEmpresa() + "!");
        return ResponseEntity.ok().body(auditoria);
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaModel>> listarAuditorias() {
        List<AuditoriaModel> auditorias = auditoriaService.listar();
        return ResponseEntity.ok(auditorias);
    }

    @GetMapping("/clientid/{idCliente}")
    public ResponseEntity<List<AuditoriaModel>> listarPorCliente(
            @PathVariable Long idCliente) {

        List<AuditoriaModel> auditorias = auditoriaService.listarPorClienteId(idCliente);
        return ResponseEntity.ok(auditorias);
    }

}