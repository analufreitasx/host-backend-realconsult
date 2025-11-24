package com.puc.realconsult.model.realConsult;

import com.puc.realconsult.model.vtRealModel.ClienteModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lembretes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LembreteModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @NotNull(message = "Data e horário são obrigatórios")
    @Column(name = "data_horario", nullable = false)
    private LocalDateTime dataHorario;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

}

