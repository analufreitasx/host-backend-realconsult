package com.puc.realconsult.repository.realConsult;

import com.puc.realconsult.model.realConsult.DespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaModel, Long> {
    
    @Query("SELECT d FROM DespesaModel d WHERE " +
           "LOWER(d.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(d.categoria) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<DespesaModel> findByTermo(@Param("termo") String termo);
    
    List<DespesaModel> findByCategoria(String categoria);
    
    List<DespesaModel> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
    
    List<DespesaModel> findByCategoriaAndDataBetween(String categoria, LocalDate dataInicio, LocalDate dataFim);

    List<DespesaModel> findAllByOrderByDataDesc();

    List<DespesaModel> findByTituloContainingIgnoreCase(String titulo);
}
