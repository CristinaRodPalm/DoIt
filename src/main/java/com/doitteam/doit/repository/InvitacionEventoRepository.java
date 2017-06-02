package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Evento;
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

    @Query("select evento from Evento evento where " +
    " evento not in (select invitacionEvento.evento from InvitacionEvento invitacionEvento where " +
        " invitacionEvento.horaResolucion is not null and invitacionEvento.resolucion = true " +
        " and invitacionEvento.invitado.id =:currentUser)")
    List<Evento> findEventosNotSigned(@Param("currentUser") Long currentUser);

    //coger el usuario logeado y comprobar si tiene invitaciones a eventos pendientes
    @Query("select invitacionEvento from InvitacionEvento invitacionEvento where " +
    " invitacionEvento.horaResolucion is null and invitacionEvento.invitado.id =:currentUser")
    List<InvitacionEvento> findPendingInvitations(@Param("currentUser") Long currentUser);

}
