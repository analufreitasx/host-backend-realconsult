package com.puc.realconsult.model.vtRealModel;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "uf_default", length = 20)
    private String ufDefault;

    @Column(name = "base_cliente", length = 100)
    private String baseCliente;

    @Column(name = "base_mapa_cliente", length = 40)
    private String baseMapaCliente;

    @Column(name = "nome_empresa", length = 30)
    private String nomeEmpresa;

    @Column(name = "cnpj", length = 20)
    private String cnpj;

    @Column(name = "path", length = 100, nullable = false)
    private String path;

    @Column(name = "numero_consultas", nullable = false)
    private Integer numeroConsultas;

    @Column(name = "numero_consultas_realizadas", nullable = false)
    private Integer numeroConsultasRealizadas;

    @Column(name = "semVT")
    private Boolean semVT;

    @Column(name = "tipoCartao", length = 50, nullable = false)
    private String tipoCartao;

    @Column(name = "tipoRoteirizacao", length = 2, nullable = false)
    private String tipoRoteirizacao;

    @Column(name = "perfil_consulta", length = 10, nullable = false)
    private String perfilConsulta;
}
