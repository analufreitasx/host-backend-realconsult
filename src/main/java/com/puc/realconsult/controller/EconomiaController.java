package com.puc.realconsult.controller;

import com.puc.realconsult.dto.EconomiaDashboardDTO;
import com.puc.realconsult.service.ClienteService;
import com.puc.realconsult.service.EconomiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/economies")
public class EconomiaController {

    @Autowired
    private EconomiaService economiaService;

    @GetMapping("/dashboard")
    public ResponseEntity<EconomiaDashboardDTO> getEconomiaDashboard(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        try {
            EconomiaDashboardDTO dto = economiaService.obterEconomiaDashboard(
                    clienteId,
                    inicio,
                    fim
            );
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
