package com.puc.realconsult.controller;

import com.puc.realconsult.dto.RequisicoesDashboardClienteDTO;
import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.repository.vtRealRepository.ClienteApiRepository;
import com.puc.realconsult.service.RequisicoesService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/requisicoes")
@AllArgsConstructor
public class RequisicoesController {

    private final RequisicoesService requisicoesService;
    private final ClienteApiRepository clienteApiRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<List<RequisicoesDashboardClienteDTO>> getRequisicoesDashboard(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        List<RequisicoesDashboardClienteDTO> dto = requisicoesService.obterRequisicoesDashboard(
                status,
                ano,
                inicio,
                fim
        );

        for (RequisicoesDashboardClienteDTO requisicao : dto) {
            ClienteModel cliente = clienteApiRepository.findByIdCliente(requisicao.getIdCliente())
                    .orElse(null);

            if (cliente != null) {
                requisicao.setNomeEmpresa(cliente.getNomeEmpresa());
            }
        }

        return ResponseEntity.ok(dto);
    }


    @GetMapping("/dashboard/{idCliente}")
    public ResponseEntity<RequisicoesDashboardClienteDTO> getRequisicoesDashboardCliente(
            @PathVariable Long idCliente,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        RequisicoesDashboardClienteDTO dto = requisicoesService.obterRequisicoesDashboardCliente(
                idCliente,
                status,
                ano,
                inicio,
                fim
        );

        ClienteModel cliente = clienteApiRepository.findById(idCliente).orElse(null);

        if (cliente != null) {
            dto.setNomeEmpresa(cliente.getNomeEmpresa());
        }

        return ResponseEntity.ok(dto);
    }

}
