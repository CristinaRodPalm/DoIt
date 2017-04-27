package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.LikesReto;
import com.doitteam.doit.repository.LikesRetoRepository;
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
 * REST controller for managing LikesReto.
 */
@RestController
@RequestMapping("/api")
public class LikesRetoResource {

    private final Logger log = LoggerFactory.getLogger(LikesRetoResource.class);

    private static final String ENTITY_NAME = "likesReto";

    private final LikesRetoRepository likesRetoRepository;

    public LikesRetoResource(LikesRetoRepository likesRetoRepository) {
        this.likesRetoRepository = likesRetoRepository;
    }

    /**
     * POST  /likes-retos : Create a new likesReto.
     *
     * @param likesReto the likesReto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new likesReto, or with status 400 (Bad Request) if the likesReto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/likes-retos")
    @Timed
    public ResponseEntity<LikesReto> createLikesReto(@RequestBody LikesReto likesReto) throws URISyntaxException {
        log.debug("REST request to save LikesReto : {}", likesReto);
        if (likesReto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new likesReto cannot already have an ID")).body(null);
        }
        LikesReto result = likesRetoRepository.save(likesReto);
        return ResponseEntity.created(new URI("/api/likes-retos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /likes-retos : Updates an existing likesReto.
     *
     * @param likesReto the likesReto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated likesReto,
     * or with status 400 (Bad Request) if the likesReto is not valid,
     * or with status 500 (Internal Server Error) if the likesReto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/likes-retos")
    @Timed
    public ResponseEntity<LikesReto> updateLikesReto(@RequestBody LikesReto likesReto) throws URISyntaxException {
        log.debug("REST request to update LikesReto : {}", likesReto);
        if (likesReto.getId() == null) {
            return createLikesReto(likesReto);
        }
        LikesReto result = likesRetoRepository.save(likesReto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, likesReto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /likes-retos : get all the likesRetos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of likesRetos in body
     */
    @GetMapping("/likes-retos")
    @Timed
    public List<LikesReto> getAllLikesRetos() {
        log.debug("REST request to get all LikesRetos");
        List<LikesReto> likesRetos = likesRetoRepository.findAll();
        return likesRetos;
    }

    /**
     * GET  /likes-retos/:id : get the "id" likesReto.
     *
     * @param id the id of the likesReto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the likesReto, or with status 404 (Not Found)
     */
    @GetMapping("/likes-retos/{id}")
    @Timed
    public ResponseEntity<LikesReto> getLikesReto(@PathVariable Long id) {
        log.debug("REST request to get LikesReto : {}", id);
        LikesReto likesReto = likesRetoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(likesReto));
    }

    /**
     * DELETE  /likes-retos/:id : delete the "id" likesReto.
     *
     * @param id the id of the likesReto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/likes-retos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLikesReto(@PathVariable Long id) {
        log.debug("REST request to delete LikesReto : {}", id);
        likesRetoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
