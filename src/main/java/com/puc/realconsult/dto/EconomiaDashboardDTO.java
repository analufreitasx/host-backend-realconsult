package com.puc.realconsult.dto;

import java.time.LocalDate;
import java.util.List;

public record EconomiaDashboardDTO(
        Long clienteId,
        LocalDate dataInicio,
        LocalDate dataFim,
        Double economiaPrevistaPeriodo,
        Double economiaRealPeriodo,
        Double diferencaPercentualPeriodo,
        Double totalAcumuladoAno,
        List<EconomiaResumoMensalDTO> economiasMensais
) {}
