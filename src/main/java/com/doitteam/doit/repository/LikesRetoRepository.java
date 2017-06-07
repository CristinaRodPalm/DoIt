package com.doitteam.doit.repository;

import com.doitteam.doit.domain.LikesReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LikesReto entity.
 */
@SuppressWarnings("unused")
public interface LikesRetoRepository extends JpaRepository<LikesReto,Long> {

    @Query("select likesReto from LikesReto likesReto where likesReto.usuario.login = ?#{principal.username}")
    List<LikesReto> findByUsuarioIsCurrentUser();

    @Query("select likesReto from LikesReto likesReto where likesReto.usuario.id=:idUser and likesReto.participacionReto.id=:idParticipacion")
    LikesReto findLikeReto(@Param("idUser") Long idUser, @Param("idParticipacion") Long idParticipacion);

}
