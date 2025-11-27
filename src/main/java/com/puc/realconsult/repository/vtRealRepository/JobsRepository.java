package com.puc.realconsult.repository.vtRealRepository;

import com.puc.realconsult.model.vtRealModel.JobsModel;
import com.puc.realconsult.model.vtRealModel.UsuarioAPIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface JobsRepository extends JpaRepository<JobsModel, Integer> {

    List<JobsModel> findByLogin(String login);

    @Query("""
        SELECT j
        FROM JobsModel j
        WHERE (:status IS NULL OR j.status = :status)
          AND (:inicio IS NULL OR j.dataInicio >= :inicio)
          AND (:fim    IS NULL OR j.dataInicio <= :fim)
        """)
    List<JobsModel> findByFiltros(@Param("status") String status,
                                  @Param("inicio") LocalDateTime inicio,
                                  @Param("fim") LocalDateTime fim);

    @Query("""
        SELECT j
        FROM JobsModel j
        JOIN UsuarioAPIModel u ON u.login = j.login
        WHERE u.idCliente.idCliente = :idCliente
          AND (:status IS NULL OR j.status = :status)
          AND (:inicio IS NULL OR j.dataInicio >= :inicio)
          AND (:fim    IS NULL OR j.dataInicio <= :fim)
        """)
    List<JobsModel> findByClienteEFiltros(@Param("idCliente") Long idCliente,
                                          @Param("status") String status,
                                          @Param("inicio") LocalDateTime inicio,
                                          @Param("fim") LocalDateTime fim);
}
