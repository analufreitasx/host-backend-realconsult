package com.puc.realconsult.repository.realConsult;
import com.puc.realconsult.model.realConsult.CategoriaDespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesaModel, Long> {
    Optional<CategoriaDespesaModel> findByNome(String nome);
}
