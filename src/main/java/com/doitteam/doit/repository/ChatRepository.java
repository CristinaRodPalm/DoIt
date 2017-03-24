package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Chat entity.
 */

public interface ChatRepository extends JpaRepository<Chat,Long> {

}
