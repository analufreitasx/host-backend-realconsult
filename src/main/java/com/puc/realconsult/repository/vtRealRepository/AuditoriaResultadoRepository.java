package com.puc.realconsult.repository.vtRealRepository;

import com.puc.realconsult.model.vtRealModel.AuditoriaResultadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaResultadoRepository extends JpaRepository<AuditoriaResultadoModel, Long> {

    @Query("""
        SELECT a
        FROM AuditoriaResultadoModel a
        WHERE (:status IS NULL OR a.status = :status)
          AND (:inicio IS NULL OR a.dataEntrada >= :inicio)
          AND (:fim IS NULL OR a.dataEntrada <= :fim)
        """)
    List<AuditoriaResultadoModel> findByFiltros(@Param("status") String status,
                                                @Param("inicio") LocalDateTime inicio,
                                                @Param("fim") LocalDateTime fim);

    @Query("""
        SELECT a
        FROM AuditoriaResultadoModel a
        WHERE a.idCliente = :idCliente
          AND (:status IS NULL OR a.status = :status)
          AND (:inicio IS NULL OR a.dataEntrada >= :inicio)
          AND (:fim IS NULL OR a.dataEntrada <= :fim)
        """)
    List<AuditoriaResultadoModel> findByClienteEFiltros(@Param("idCliente") Long idCliente,
                                                        @Param("status") String status,
                                                        @Param("inicio") LocalDateTime inicio,
                                                        @Param("fim") LocalDateTime fim);
}
