package com.puc.realconsult.dto;

public record EconomiaResumoMensalDTO(
        Integer ano,
        Integer mes,
        Double economiaPrevista,
        Double economiaReal
) {}
