package com.doitteam.doit.repository;

import com.doitteam.doit.domain.InvitacionEvento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InvitacionEvento entity.
 */
@SuppressWarnings("unused")
public interface InvitacionEventoRepository extends JpaRepository<InvitacionEvento,Long> {

    @Query("select invitacionEvento from InvitacionEvento invitacionEvento where invitacionEvento.miembroEvento.login = ?#{principal.username}")
    List<InvitacionEvento> findByMiembroEventoIsCurrentUser();

    @Query("select invitacionEvento from InvitacionEvento invitacionEvento where invitacionEvento.invitado.login = ?#{principal.username}")
    List<InvitacionEvento> findByInvitadoIsCurrentUser();

}
