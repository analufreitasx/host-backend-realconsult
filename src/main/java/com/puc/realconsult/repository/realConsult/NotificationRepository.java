package com.puc.realconsult.repository.realConsult;

import com.puc.realconsult.model.realConsult.Notification;
import com.puc.realconsult.utils.StatusNotificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUsuarioIdAndStatus(Long usuarioId, StatusNotificacao status, Pageable pageable);

}



