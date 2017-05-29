package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Amistad;
import com.doitteam.doit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data JPA repository for the Amistad entity.
 */
@SuppressWarnings("unused")
public interface AmistadRepository extends JpaRepository<Amistad,Long> {

    @Query("select amistad from Amistad amistad where amistad.emisor.login = ?#{principal.username}")
    List<Amistad> findByEmisorIsCurrentUser();

    @Query("select amistad from Amistad amistad where " +
        "amistad.receptor.id =:currentUser " +
        "and amistad.horaRespuesta = null")
    List<Amistad> findByReceptorIsCurrentUser(@Param("currentUser") Long currentUser);

    @Query("select amistad from Amistad amistad where amistad.id =:id")
    Amistad findById(@Param("id") Long id);

    // todas las amistades aceptadas donde el emisor y receptor es el user login --> HAN SIDO ACEPTADAS
    @Query("select amistad from Amistad amistad where " +
        "amistad.aceptada = true " +
        "and amistad.receptor.id =:currentUser " +
        "or amistad.emisor.id =:currentUser")
    List<Amistad> findAllFriends(@Param("currentUser") Long currentUser);

    // todas las amistades aceptadas donde el emisor y receptor es el user login --> QUE ESTÁN PENDIENTES
    @Query("select amistad from Amistad amistad where " +
        "amistad.horaRespuesta is null and amistad.aceptada is null " +
        "and (amistad.receptor.id =:currentUser or amistad.emisor.id =:currentUser)")
    List<Amistad> findFriendsPendingRequest(@Param("currentUser") Long currentUser);

    //NO TOCAR, MUESTRA AMIGOS
    @Query("select amistad from Amistad amistad where " +
        "amistad.horaRespuesta is not null and amistad.aceptada = true " +
        "and (amistad.receptor.id =:currentUser or amistad.emisor.id =:currentUser)")
    List<Amistad> findFriendsAcceptedRequest(@Param("currentUser") Long currentUser);

    // los que ya son amigos
    // los que tienen una solicitud
    @Query("select amistad from Amistad amistad where amistad.receptor.id=:currentUser or amistad.emisor.id=:currentUser")
    List<Amistad> findAmigos(@Param("currentUser") Long currentUser);

    //esta query esta relacionada con la api que no se como funciona
    @Query("select amistad from Amistad amistad where amistad.emisor.id=:otherUser and amistad.receptor.id=:currentUser")
    Amistad findOneByEmisorReceptor(@Param("currentUser") Long currentUser, @Param("otherUser") Long otherUser);

    //para obtener todos los demas usuarios que no son amigos ni has enviado peticion
    /*@Query("select userExt.user from UserExt userExt where " +
        "userExt not in (select amistad from Amistad amistad where " +
        "amistad.emisor!= :currentUser or amistad.receptor!= :currentUser)")
    List<User> findOtherUsers(@Param("currentUser") Long currentUser);

    //query de ejemplo para ver como funcionan los subselect
    @Query("select evento from Evento evento where " +
        " evento not in (select invitacionEvento.evento from InvitacionEvento invitacionEvento where " +
        " invitacionEvento.horaResolucion is not null and invitacionEvento.resolucion = true " +
        " and invitacionEvento.invitado.id =:currentUser)")
    List<Evento> findEventosNotSigned(@Param("currentUser") Long currentUser);*/
}
