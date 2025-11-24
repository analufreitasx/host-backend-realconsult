package com.puc.realconsult.repository.realConsult;

import com.puc.realconsult.model.realConsult.AuditoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<AuditoriaModel, Long> {

    List<AuditoriaModel> findByIdCliente(Long clienteId);

}