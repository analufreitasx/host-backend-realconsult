package com.puc.realconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoConsultaDTO {

    private String id;
    private LocalDateTime dataConsulta;
    private String status;
    private String origem;
    private String destino;
    private String result;
}
