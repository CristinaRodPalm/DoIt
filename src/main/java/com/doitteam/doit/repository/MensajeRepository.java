package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Mensaje;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mensaje entity.
 */
@SuppressWarnings("unused")
public interface MensajeRepository extends JpaRepository<Mensaje,Long> {

    @Query("select mensaje from Mensaje mensaje where mensaje.emisor.login = ?#{principal.username}")
    List<Mensaje> findByEmisorIsCurrentUser();

    @Query("select mensaje from Mensaje mensaje where mensaje.receptor.login = ?#{principal.username}")
    List<Mensaje> findByReceptorIsCurrentUser();

}
