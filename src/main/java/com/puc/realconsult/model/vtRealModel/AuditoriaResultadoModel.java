package com.puc.realconsult.model.vtRealModel;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import com.puc.realconsult.utils.JsonUtil;

@Entity
@Table(name = "auditoria_resultado")
@Data
public class AuditoriaResultadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Long idResultado;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Lob
    @Column(name = "request", nullable = false)
    private String request;

    @Column(name = "end_origem", length = 255, nullable = false)
    private String endOrigem;

    @Column(name = "end_destino", length = 255, nullable = false)
    private String endDestino;

    @Column(name = "mat_funcionario")
    private Integer matFuncionario;

    @Column(name = "id_unidade")
    private Integer idUnidade;

    @Column(name = "login", length = 100)
    private String login;

    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @Column(name = "gerouDO", nullable = false)
    private Boolean gerouDO;

    @Column(name = "tempo_processamento", nullable = false)
    private Double tempoProcessamento;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        boolean[] first = { true };

        JsonUtil.appendNumber(sb, "idResultado", idResultado, first);

        Integer idCliente = getIdCliente();
        JsonUtil.appendNumber(sb, "idCliente", idCliente, first);

        JsonUtil.appendJsonOrString(sb, "request", request, first);

        JsonUtil.appendJsonOrString(sb, "endOrigem", endOrigem, first);
        JsonUtil.appendJsonOrString(sb, "endDestino", endDestino, first);
        JsonUtil.appendNumber(sb, "matFuncionario", matFuncionario, first);
        JsonUtil.appendNumber(sb, "idUnidade", idUnidade, first);
        JsonUtil.appendJsonOrString(sb, "login", login, first);
        JsonUtil.appendJsonOrString(sb, "status", status, first);
        JsonUtil.appendBoolean(sb, "gerouDO", gerouDO, first);
        JsonUtil.appendNumber(sb, "tempoProcessamento", tempoProcessamento, first);
        JsonUtil.appendDateTime(sb, "dataEntrada", dataEntrada, first);

        sb.append("\n}");
        return sb.toString();
    }


}
