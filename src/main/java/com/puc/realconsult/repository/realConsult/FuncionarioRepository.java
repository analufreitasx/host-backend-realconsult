package com.puc.realconsult.repository.realConsult;

import com.puc.realconsult.model.realConsult.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long> {

    List<FuncionarioModel> findByAuditoriaId(Long auditoriaId);

    List<FuncionarioModel> findByAuditoriaIdAndNomeFuncionarioContainingIgnoreCase(Long auditoriaId, String nome);

    FuncionarioModel findByAuditoriaIdAndMatricula(Long auditoriaId, Long matricula);

    @Query("""
        SELECT f
        FROM FuncionarioModel f
        WHERE (:clienteId IS NULL OR f.auditoria.idCliente = :clienteId)
          AND (:inicio IS NULL OR f.auditoria.creationDate >= :inicio)
          AND (:fim    IS NULL OR f.auditoria.creationDate <= :fim)
    """)
    List<FuncionarioModel> findByFiltros(
            @Param("clienteId") Long clienteId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
