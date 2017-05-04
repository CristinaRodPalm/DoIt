package com.doitteam.doit.repository;

import com.doitteam.doit.domain.InvitacionEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("select invitacionEvento from InvitacionEvento invitacionEvento where " +
        "invitacionEvento.horaResolucion is not null and invitacionEvento.resolucion = true " +
        " and invitacionEvento.invitado.id =:currentUser")
    List<InvitacionEvento> findEventosSigned(@Param("currentUser") Long currentUser);



}
