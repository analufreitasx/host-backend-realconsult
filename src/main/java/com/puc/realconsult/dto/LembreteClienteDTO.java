package com.puc.realconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LembreteClienteDTO {

    private Long id;
    private Long idCliente;
    private String nomeEmpresa;
    private String cnpj;
    private LocalDateTime dataHorario;
    private LocalDateTime dataCriacao;

}
