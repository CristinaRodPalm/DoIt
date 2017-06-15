package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Chat;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Chat entity.
 */
@SuppressWarnings("unused")
public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Query("select chat from Chat chat where chat.emisor=:idEmisor and chat.receptor=:idReceptor")
    Chat findOneByEmisorReceptor(@Param("idEmisor") Long ididEmisor, @Param("idReceptor") Long idReceptor);

}
