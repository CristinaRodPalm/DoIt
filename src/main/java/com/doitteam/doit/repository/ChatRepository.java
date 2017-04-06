package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Chat;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Chat entity.
 */
@SuppressWarnings("unused")
public interface ChatRepository extends JpaRepository<Chat,Long> {

}
