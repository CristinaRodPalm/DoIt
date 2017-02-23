package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Amistad;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Amistad entity.
 */
@SuppressWarnings("unused")
public interface AmistadRepository extends JpaRepository<Amistad,Long> {

    @Query("select amistad from Amistad amistad where amistad.emisor.login = ?#{principal.username}")
    List<Amistad> findByEmisorIsCurrentUser();

    @Query("select amistad from Amistad amistad where amistad.receptor.login = ?#{principal.username}")
    List<Amistad> findByReceptorIsCurrentUser();

}
