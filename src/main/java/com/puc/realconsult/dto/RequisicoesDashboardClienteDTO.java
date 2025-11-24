package com.puc.realconsult.dto;

import com.puc.realconsult.model.vtRealModel.JobsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequisicoesDashboardClienteDTO {

    private Long idCliente;

    private String nomeEmpresa;

    private Integer consultasContratadas;

    private Integer consultasRealizadas;

    private Integer saldoConsultas;

    private List<HistoricoConsultaDTO> historicoConsultas;
}
