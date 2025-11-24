package com.puc.realconsult.repository.realConsult;

import com.puc.realconsult.model.realConsult.ReceitaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaModel, Long> {

    @Query("SELECT r FROM ReceitaModel r WHERE " +
            "LOWER(r.titulo) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<ReceitaModel> findByTermo(@Param("termo") String termo);

    List<ReceitaModel> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    List<ReceitaModel> findAllByOrderByDataDesc();

    List<ReceitaModel> findByTituloContainingIgnoreCase(String titulo);
}
