package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Reto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Reto entity.
 */
@SuppressWarnings("unused")
public interface RetoRepository extends JpaRepository<Reto,Long> {

}
