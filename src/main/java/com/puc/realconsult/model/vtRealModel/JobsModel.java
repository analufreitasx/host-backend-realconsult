package com.puc.realconsult.model.vtRealModel;

import com.puc.realconsult.utils.JsonUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ws_jobs")
@Data
public class JobsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_job")
    private Long idJob;

    @Column(name = "session_id")
    private Integer sessionId;

    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @Column(name = "begin", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "begin_status")
    private LocalDateTime beginStatus;

    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @Column(name = "status_retorno", length = 2000)
    private String statusRetorno;

    @Column(name = "ida_volta", nullable = false)
    private Integer idaVolta;

    @Column(name = "hora_entrada")
    private Integer horaEntrada;

    @Column(name = "hora_saida")
    private Integer horaSaida;

    @Column(name = "raio")
    private Double raio;

    @Column(name = "tipo_dia", length = 10)
    private String tipoDia;

    @Column(name = "qtde_linhas", nullable = false)
    private Integer qtdeLinhas;

    @Column(name = "origin_address", length = 255)
    private String originAddress;

    @Column(name = "destination_address", length = 255)
    private String destinationAddress;

    @Column(name = "enable_walking", nullable = false)
    private Boolean enableWalking;

    @Column(name = "distance", nullable = false)
    private Integer distance;

    @Column(name = "xml_result", length = 50)
    private String xmlResult;

    @Column(name = "time_proc")
    private Double timeProc;

    @Column(name = "cpu_average")
    private Double cpuAverage;

    @Column(name = "memory")
    private Double memory;

    @Column(name = "id_batch")
    private Integer idBatch;

    @Column(name = "proposed_value")
    private Double proposedValue;

    @Column(name = "qtde_dias_mes")
    private Integer qtdeDiasMes;

    @Column(name = "vale_dia")
    private Double valeDia;

    @Column(name = "lat_origem")
    private Double latOrigem;

    @Column(name = "long_origem")
    private Double longOrigem;

    @Column(name = "lat_destino")
    private Double latDestino;

    @Column(name = "long_destino")
    private Double longDestino;

    @Column(name = "escolha_ida")
    private Integer escolhaIda;

    @Column(name = "escolha_volta")
    private Integer escolhaVolta;

    @Column(name = "job_info", length = 1000)
    private String jobInfo;

    @Column(name = "informacoes_adicionais", length = 300)
    private String informacoesAdicionais;

    @Column(name = "consulta_valida", nullable = false)
    private Boolean consultaValida;

    @Column(name = "error_msg", length = 300)
    private String errorMsg;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        boolean[] first = { true };

        JsonUtil.appendNumber(sb, "idJob", idJob, first);
        JsonUtil.appendNumber(sb, "sessionId", sessionId, first);
        JsonUtil.appendString(sb, "login", login, first);
        JsonUtil.appendDateTime(sb, "begin", dataInicio, first);
        JsonUtil.appendDateTime(sb, "beginStatus", beginStatus, first);
        JsonUtil.appendString(sb, "status", status, first);

        JsonUtil.appendJsonOrString(sb, "statusRetorno", statusRetorno, first);

        JsonUtil.appendNumber(sb, "idaVolta", idaVolta, first);
        JsonUtil.appendNumber(sb, "horaEntrada", horaEntrada, first);
        JsonUtil.appendNumber(sb, "horaSaida", horaSaida, first);
        JsonUtil.appendNumber(sb, "raio", raio, first);
        JsonUtil.appendString(sb, "tipoDia", tipoDia, first);
        JsonUtil.appendNumber(sb, "qtdeLinhas", qtdeLinhas, first);

        JsonUtil.appendJsonOrString(sb, "originAddress", originAddress, first);
        JsonUtil.appendJsonOrString(sb, "destinationAddress", destinationAddress, first);

        JsonUtil.appendBoolean(sb, "enableWalking", enableWalking, first);
        JsonUtil.appendNumber(sb, "distance", distance, first);
        JsonUtil.appendJsonOrString(sb, "xmlResult", xmlResult, first);
        JsonUtil.appendNumber(sb, "timeProc", timeProc, first);
        JsonUtil.appendNumber(sb, "cpuAverage", cpuAverage, first);
        JsonUtil.appendNumber(sb, "memory", memory, first);
        JsonUtil.appendNumber(sb, "idBatch", idBatch, first);
        JsonUtil.appendNumber(sb, "proposedValue", proposedValue, first);
        JsonUtil.appendNumber(sb, "qtdeDiasMes", qtdeDiasMes, first);
        JsonUtil.appendNumber(sb, "valeDia", valeDia, first);
        JsonUtil.appendNumber(sb, "latOrigem", latOrigem, first);
        JsonUtil.appendNumber(sb, "longOrigem", longOrigem, first);
        JsonUtil.appendNumber(sb, "latDestino", latDestino, first);
        JsonUtil.appendNumber(sb, "longDestino", longDestino, first);
        JsonUtil.appendNumber(sb, "escolhaIda", escolhaIda, first);
        JsonUtil.appendNumber(sb, "escolhaVolta", escolhaVolta, first);
        JsonUtil.appendJsonOrString(sb, "jobInfo", jobInfo, first);
        JsonUtil.appendJsonOrString(sb, "informacoesAdicionais", informacoesAdicionais, first);
        JsonUtil.appendBoolean(sb, "consultaValida", consultaValida, first);
        JsonUtil.appendJsonOrString(sb, "errorMsg", errorMsg, first);

        sb.append("\n}");
        return sb.toString();
    }

}
