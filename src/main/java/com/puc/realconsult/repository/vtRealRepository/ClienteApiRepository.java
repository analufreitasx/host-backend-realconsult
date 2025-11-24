package com.puc.realconsult.repository.vtRealRepository;

import com.puc.realconsult.model.vtRealModel.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteApiRepository extends JpaRepository<ClienteModel, Long> {
    Optional<ClienteModel> findByCnpj(String cnpj);
    Optional<ClienteModel> findByIdCliente(Long idCliente);

    @Query(value = """
        SELECT * FROM cliente 
        WHERE LOWER(nome_empresa) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR REPLACE(REPLACE(REPLACE(cnpj, '.', ''), '/', ''), '-', '') 
              LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:termo, '.', ''), '/', ''), '-', ''), '%')
    """, nativeQuery = true)
    List<ClienteModel> buscarPorNomeOuCnpj(@Param("termo") String termo);

}
