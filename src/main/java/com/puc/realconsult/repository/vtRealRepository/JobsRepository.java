package com.puc.realconsult.repository.vtRealRepository;

import com.puc.realconsult.model.vtRealModel.JobsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface JobsRepository extends JpaRepository<JobsModel,Long> {

    List<JobsModel> findByLogin(String login);

    @Query(value = """
    SELECT begin FROM ws_jobs
    WHERE begin >= CAST(CONCAT(:ano, '-01-01') AS DATETIME)
      AND begin <  CAST(CONCAT(:ano + 1, '-01-01') AS DATETIME)
      AND login = :login
      AND status = '3'""", nativeQuery = true)
    List<Timestamp> buscarTodasReqDatas(@Param("ano") Integer ano, @Param("login" ) String login);

    @Query(value = """
            SELECT COUNT(BEGIN) FROM ws_jobs
            WHERE begin >= :inicio 
            AND begin <= :fim
            AND login = :login """, nativeQuery = true)
    Integer buscarReqPeriodoComLogin(@Param("inicio") Timestamp inicio,
                                     @Param("fim") Timestamp fim,
                                     @Param("login") String login);

    @Query(value = """
            SELECT COUNT(BEGIN) FROM ws_jobs
            WHERE begin >= :inicio 
            AND begin <= :fim
            AND login = :login
            AND status = '3'""", nativeQuery = true)
    Integer buscarReqPeriodoComLoginStatus3(@Param("inicio") Timestamp inicio,
                                            @Param("fim") Timestamp fim,
                                            @Param("login") String login);

    @Query(value = """
            SELECT * FROM ws_jobs
            WHERE begin >= :inicio 
            AND begin <= :fim
            AND login = :login """, nativeQuery = true)
    List<JobsModel> buscarReqPeriodoComTodosOsCampos(@Param("inicio") Timestamp inicio,
                                                     @Param("fim") Timestamp fim,
                                                     @Param("login") String login);

    @Query(value = """
            SELECT COUNT(BEGIN) FROM ws_jobs
            WHERE login = :login
            """,  nativeQuery = true)
    Integer buscarTodasReqPorLogin(@Param("login") String login);

    @Query(value = """
            SELECT COUNT(BEGIN) FROM ws_jobs
            WHERE login = :login
            AND status = '3'
            """,  nativeQuery = true)
    Integer buscarTodasReqPorLoginStatus3(@Param("login") String login);

    @Query(value = """
            SELECT * FROM ws_jobs
            WHERE login = :login """, nativeQuery = true)
    List<JobsModel> buscarReqComTodosOsCampos(@Param("login") String login);


    @Query(value = """
        SELECT * 
        FROM ws_jobs j
        WHERE (:status IS NULL OR j.status = :status)
          AND (:inicio IS NULL OR j.begin >= :inicio)
          AND (:fim IS NULL OR j.begin <= :fim)
        """, nativeQuery = true)
    List<JobsModel> findByFiltros(@Param("status") String status,
                                  @Param("inicio") LocalDateTime inicio,
                                  @Param("fim") LocalDateTime fim);

    @Query(value = """
        SELECT j.* 
        FROM ws_jobs j
        JOIN usuario u ON u.login = j.login
        WHERE u.id_cliente = :idCliente
          AND (:status IS NULL OR j.status = :status)
          AND (:inicio IS NULL OR j.begin >= :inicio)
          AND (:fim IS NULL OR j.begin <= :fim)
        """, nativeQuery = true)
    List<JobsModel> findByClienteEFiltros(@Param("idCliente") Long idCliente,
                                          @Param("status") String status,
                                          @Param("inicio") LocalDateTime inicio,
                                          @Param("fim") LocalDateTime fim);
}
