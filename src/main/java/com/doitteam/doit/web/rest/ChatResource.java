package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Chat;
import com.doitteam.doit.domain.User;
import com.doitteam.doit.repository.ChatRepository;
import com.doitteam.doit.repository.UserRepository;
import com.doitteam.doit.security.SecurityUtils;
import com.doitteam.doit.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Chat.
 */
@RestController
@RequestMapping("/api")
public class ChatResource {

    private final Logger log = LoggerFactory.getLogger(ChatResource.class);

    private static final String ENTITY_NAME = "chat";

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatResource(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }


    // Create chat
    @PostMapping("/chatByID/{idReceptor}")
    @Timed
    public ResponseEntity<Chat> createChatByID(@PathVariable Long idReceptor) throws URISyntaxException{
        Chat chat = new Chat();
        chat.setEmisor(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        chat.setReceptor(idReceptor);

Chat result = chatRepository.save(chat);
        return ResponseEntity.created(new URI("/api/chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);    }

    /**
     * POST  /chats : Create a new chat.
     *
     * @param chat the chat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chat, or with status 400 (Bad Request) if the chat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chats")
    @Timed
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) throws URISyntaxException {
        log.debug("REST request to save Chat : {}", chat);
        if (chat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new chat cannot already have an ID")).body(null);
        }
        Chat result = chatRepository.save(chat);
        return ResponseEntity.created(new URI("/api/chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chats : Updates an existing chat.
     *
     * @param chat the chat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chat,
     * or with status 400 (Bad Request) if the chat is not valid,
     * or with status 500 (Internal Server Error) if the chat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chats")
    @Timed
    public ResponseEntity<Chat> updateChat(@RequestBody Chat chat) throws URISyntaxException {
        log.debug("REST request to update Chat : {}", chat);
        if (chat.getId() == null) {
            return createChat(chat);
        }
        Chat result = chatRepository.save(chat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chats : get all the chats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chats in body
     */
    @GetMapping("/chats")
    @Timed
    public List<Chat> getAllChats() {
        log.debug("REST request to get all Chats");
        List<Chat> chats = chatRepository.findAll();
        return chats;
    }

    /**
     * GET  /chats/:id : get the "id" chat.
     *
     * @param id the id of the chat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chat, or with status 404 (Not Found)
     */
    @GetMapping("/chats/{id}")
    @Timed
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        log.debug("REST request to get Chat : {}", id);
        Chat chat = chatRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chat));
    }

    /**
     * DELETE  /chats/:id : delete the "id" chat.
     *
     * @param id the id of the chat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chats/{id}")
    @Timed
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        log.debug("REST request to delete Chat : {}", id);
        chatRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
