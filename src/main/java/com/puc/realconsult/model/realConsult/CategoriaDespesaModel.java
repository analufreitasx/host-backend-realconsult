package com.puc.realconsult.model.realConsult;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class CategoriaDespesaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(max = 50, message = "Nome da categoria deve ter no máximo 50 caracteres")
    @Column(nullable = false, unique = true)
    private String nome;


}
