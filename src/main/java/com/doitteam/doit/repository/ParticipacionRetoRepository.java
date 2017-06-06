package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Amistad;
import com.doitteam.doit.domain.ParticipacionReto;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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


    /*SELECT * FROM participacion_reto where usuario_id!=5
        and(usuario_id in
                (select receptor_id from amistad where aceptada = true and (receptor_id = 5 or emisor_id = 5))
        or participacion_reto.usuario_id in
                (select receptor_id from amistad where aceptada = true and (receptor_id = 5 or emisor_id = 5)));*/

}
