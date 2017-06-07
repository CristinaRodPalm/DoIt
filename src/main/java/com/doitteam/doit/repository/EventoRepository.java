package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Evento entity.
 */
@SuppressWarnings("unused")
public interface EventoRepository extends JpaRepository<Evento,Long> {

    @Query("select evento from Evento evento where evento.admin.login = ?#{principal.username}")
    List<Evento> findByAdminIsCurrentUser();



}
