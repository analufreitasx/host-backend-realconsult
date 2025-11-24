package com.puc.realconsult.model.vtRealModel;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_resultado")
@Data
public class AuditoriaResultadoModel {

    @Id
    @Column(name = "id_resultado")
    private Long idResultado;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "request", nullable = false, columnDefinition = "json")
    private String request;

    @Column(name = "end_origem", nullable = false, length = 255)
    private String endOrigem;

    @Column(name = "end_destino", nullable = false, length = 255)
    private String endDestino;

    @Column(name = "mat_funcionario")
    private Integer matFuncionario;

    @Column(name = "id_unidade")
    private Integer idUnidade;

    @Column(name = "login", length = 100)
    private String login;

    @Column(name = "status", nullable = false, length = 3)
    private String status;

    @Column(name = "gerouDO", nullable = false)
    private Boolean gerouDO = false;

    @Column(name = "tempo_processamento", nullable = false)
    private Double tempoProcessamento;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Override
    public String toString() {
        return "{" +
                "\"idResultado\":" + idResultado + "," +
                "\"idCliente\":" + idCliente + "," +
                "\"request\":" + request + "," +
                "\"endOrigem\":\"" + endOrigem + "\"," +
                "\"endDestino\":\"" + endDestino + "\"," +
                "\"matFuncionario\":" + matFuncionario + "," +
                "\"idUnidade\":" + idUnidade + "," +
                "\"login\":\"" + login + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"gerouDO\":" + gerouDO + "," +
                "\"tempoProcessamento\":" + tempoProcessamento + "," +
                "\"dataEntrada\":\"" + dataEntrada + "\"" +
                "}";
    }

}
