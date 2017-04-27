package com.doitteam.doit.repository;

import com.doitteam.doit.domain.LikesReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the LikesReto entity.
 */
@SuppressWarnings("unused")
public interface LikesRetoRepository extends JpaRepository<LikesReto,Long> {

    @Query("select likesReto from LikesReto likesReto where likesReto.usuario.login = ?#{principal.username}")
    List<LikesReto> findByUsuarioIsCurrentUser();

}
