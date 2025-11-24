package com.puc.realconsult.repository.vtRealRepository;

import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.model.vtRealModel.UsuarioAPIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioApiRepository extends JpaRepository<UsuarioAPIModel, Long> {

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByIdCliente(ClienteModel cliente);

    UsuarioAPIModel findByLogin(String login);

    UsuarioAPIModel findByEmail(String email);

    @Query("SELECT u FROM UsuarioAPIModel u WHERE u.idCliente.idCliente = :idCliente")
    UsuarioAPIModel findByIdCliente(@Param("idCliente") Long idCliente);

    @Query("SELECT u.idCliente FROM UsuarioAPIModel u WHERE u.login = :login")
    ClienteModel findClienteByLogin(@Param("login") String login);

    @Query("SELECT u.login FROM UsuarioAPIModel u WHERE u.idCliente = :cliente")
    String findLoginByCliente(@Param("cliente") ClienteModel cliente);
}
