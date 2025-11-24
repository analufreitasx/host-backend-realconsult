package com.puc.realconsult.repository.realConsult;

import com.puc.realconsult.dto.LembreteClienteDTO;
import com.puc.realconsult.model.realConsult.LembreteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LembreteRepository extends JpaRepository<LembreteModel, Long> {
    
    List<LembreteModel> findAllByOrderByDataHorarioAsc();
    
    List<LembreteModel> findByIdCliente(Long idCliente);
    
}

