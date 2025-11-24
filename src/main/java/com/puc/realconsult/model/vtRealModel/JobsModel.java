

package com.puc.realconsult.model.vtRealModel;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "ws_jobs")
@Data
public class JobsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_job")
    private Integer idJob;

    @Column(name = "session_id")
    private Integer sessionId;

    @Column(length = 50, nullable = false)
    private String login;

    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp begin;

    @Column(name = "begin_status")
    private Timestamp beginStatus;

    @Column(length = 3, nullable = false)
    private String status;

    @Column(name = "status_retorno", length = 2000)
    private String statusRetorno;

    @Column(name = "ida_volta", nullable = false)
    private Integer idaVolta;

    @Column(name = "hora_entrada")
    private Integer horaEntrada;

    @Column(name = "hora_saida")
    private Integer horaSaida;

    @Column
    private Float raio;

    @Column(name = "tipo_dia", length = 10)
    private String tipoDia;

    @Column(name = "qtde_linhas")
    private Integer qtdeLinhas;

    @Column(name = "origin_address", length = 255)
    private String originAddress;

    @Column(name = "destination_address", length = 255)
    private String destinationAddress;

    @Column(name = "enable_walking", nullable = false)
    private Boolean enableWalking;

    @Column(nullable = false)
    private Integer distance;

    @Column(name = "xml_result", length = 50)
    private String xmlResult;

    @Column(name = "time_proc")
    private Double timeProc;

    @Column(name = "cpu_average")
    private Double cpuAverage;

    @Column
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
        return "{" +
                "\"idJob\":" + idJob + "," +
                "\"sessionId\":" + sessionId + "," +
                "\"login\":\"" + login + "\"," +
                "\"begin\":\"" + begin + "\"," +
                "\"beginStatus\":\"" + beginStatus + "\"," +
                "\"status\":\"" + status + "\"," +

                "\"statusRetorno\":" + (statusRetorno != null ? (statusRetorno instanceof String ? "\"" + statusRetorno + "\"" : statusRetorno.toString()) : "null") + "," +

                "\"idaVolta\":" + idaVolta + "," +
                "\"horaEntrada\":" + horaEntrada + "," +
                "\"horaSaida\":" + horaSaida + "," +
                "\"raio\":" + raio + "," +
                "\"tipoDia\":\"" + tipoDia + "\"," +
                "\"qtdeLinhas\":" + qtdeLinhas + "," +

                "\"originAddress\":" + (originAddress != null ? originAddress : "null") + "," +
                "\"destinationAddress\":" + (destinationAddress != null ? destinationAddress : "null") + "," +

                "\"enableWalking\":" + enableWalking + "," +
                "\"distance\":" + distance + "," +
                "\"xmlResult\":\"" + xmlResult + "\"," +
                "\"timeProc\":" + timeProc + "," +
                "\"cpuAverage\":" + cpuAverage + "," +
                "\"memory\":" + memory + "," +
                "\"idBatch\":" + idBatch + "," +
                "\"proposedValue\":" + proposedValue + "," +
                "\"qtdeDiasMes\":" + qtdeDiasMes + "," +
                "\"valeDia\":" + valeDia + "," +
                "\"latOrigem\":" + latOrigem + "," +
                "\"longOrigem\":" + longOrigem + "," +
                "\"latDestino\":" + latDestino + "," +
                "\"longDestino\":" + longDestino + "," +
                "\"escolhaIda\":" + escolhaIda + "," +
                "\"escolhaVolta\":" + escolhaVolta + "," +
                "\"jobInfo\":\"" + jobInfo + "\"," +
                "\"informacoesAdicionais\":\"" + informacoesAdicionais + "\"," +
                "\"consultaValida\":" + consultaValida + "," +
                "\"errorMsg\":\"" + errorMsg + "\"" +
                "}";
    }


}
