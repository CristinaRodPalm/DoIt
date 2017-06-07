package com.doitteam.doit.repository;

import com.doitteam.doit.domain.ParticipacionReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ParticipacionReto entity.
 */
@SuppressWarnings("unused")
public interface ParticipacionRetoRepository extends JpaRepository<ParticipacionReto,Long> {

    @Query("select participacionReto from ParticipacionReto participacionReto where participacionReto.usuario.login = ?#{principal.username}")
    List<ParticipacionReto> findByUsuarioIsCurrentUser();

    @Query("select participacionReto from ParticipacionReto participacionReto where participacionReto.usuario.id!=:currentUser " +
        "and (participacionReto.usuario.id in " +
            "(select amistad.receptor.id from Amistad amistad where amistad.aceptada = true and (amistad.receptor.id=:currentUser or amistad.emisor.id=:currentUser))" +
        "or participacionReto.usuario.id in " +
            "(select amistad.emisor.id from Amistad amistad where amistad.aceptada = true and (amistad.receptor.id=:currentUser or amistad.emisor.id=:currentUser)))")
    List<ParticipacionReto> getParticipacionesFriends(@Param("currentUser") Long currentUser);

    @Query("select participacionReto from ParticipacionReto participacionReto where participacionReto.usuario.id=:currentUser")
    List<ParticipacionReto> getMisParticipaciones(@Param("currentUser") Long currentUser);

    @Query("select count(likesReto) from LikesReto likesReto where likesReto.participacionReto.id=:participacionId")
    Integer getLikesParticipacion(@Param("participacionId") Long participacionId);

    @Modifying
    @Query("DELETE from LikesReto likesReto where likesReto.participacionReto.id=:idParticipacion")
    void deleteLikesParticipacion(@Param("idParticipacion") Long idParticipacion);

    @Query("select count(participacionReto) from ParticipacionReto participacionReto " +
        "where participacionReto.usuario.id=:idUser and participacionReto.reto.id=:idReto")
    Integer getExistingParticipacion(@Param("idUser") Long idUser, @Param("idReto") Long idReto);
}
