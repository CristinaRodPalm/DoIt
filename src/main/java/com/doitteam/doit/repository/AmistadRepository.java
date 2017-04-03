package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Amistad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

}
