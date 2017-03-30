package com.doitteam.doit.repository;

import com.doitteam.doit.domain.ParticipacionReto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParticipacionReto entity.
 */
@SuppressWarnings("unused")
public interface ParticipacionRetoRepository extends JpaRepository<ParticipacionReto,Long> {

    @Query("select participacionReto from ParticipacionReto participacionReto where participacionReto.usuario.login = ?#{principal.username}")
    List<ParticipacionReto> findByUsuarioIsCurrentUser();

}
