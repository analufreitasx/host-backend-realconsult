package com.puc.realconsult.model.vtRealModel;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioAPIModel {

    @Id
    @Column(name = "login", length = 40, nullable = false)
    private String login;

    @Column(name = "senha", length = 100, nullable = false)
    private String senha;

    @JoinColumn(name = "id_cliente")
    @OneToOne
    private ClienteModel idCliente;

    @Column(name = "email", length = 100)
    private String email;
}
