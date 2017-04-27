package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Reto;
import com.doitteam.doit.repository.RetoRepository;
import com.doitteam.doit.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Reto.
 */
@RestController
@RequestMapping("/api")
public class RetoResource {

    private final Logger log = LoggerFactory.getLogger(RetoResource.class);

    private static final String ENTITY_NAME = "reto";

    private final RetoRepository retoRepository;

    public RetoResource(RetoRepository retoRepository) {
        this.retoRepository = retoRepository;
    }

    /**
     * POST  /retos : Create a new reto.
     *
     * @param reto the reto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reto, or with status 400 (Bad Request) if the reto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/retos")
    @Timed
    public ResponseEntity<Reto> createReto(@RequestBody Reto reto) throws URISyntaxException {
        log.debug("REST request to save Reto : {}", reto);
        if (reto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reto cannot already have an ID")).body(null);
        }
        reto.setHoraPublicacion(ZonedDateTime.now());
        Reto result = retoRepository.save(reto);
        return ResponseEntity.created(new URI("/api/retos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /retos : Updates an existing reto.
     *
     * @param reto the reto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reto,
     * or with status 400 (Bad Request) if the reto is not valid,
     * or with status 500 (Internal Server Error) if the reto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/retos")
    @Timed
    public ResponseEntity<Reto> updateReto(@RequestBody Reto reto) throws URISyntaxException {
        log.debug("REST request to update Reto : {}", reto);
        if (reto.getId() == null) {
            return createReto(reto);
        }
        Reto result = retoRepository.save(reto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /retos : get all the retos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of retos in body
     */
    @GetMapping("/retos")
    @Timed
    public List<Reto> getAllRetos() {
        log.debug("REST request to get all Retos");
        List<Reto> retos = retoRepository.findAll();
        return retos;
    }

    /**
     * GET  /retos/:id : get the "id" reto.
     *
     * @param id the id of the reto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reto, or with status 404 (Not Found)
     */
    @GetMapping("/retos/{id}")
    @Timed
    public ResponseEntity<Reto> getReto(@PathVariable Long id) {
        log.debug("REST request to get Reto : {}", id);
        Reto reto = retoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reto));
    }

    /**
     * DELETE  /retos/:id : delete the "id" reto.
     *
     * @param id the id of the reto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/retos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReto(@PathVariable Long id) {
        log.debug("REST request to delete Reto : {}", id);
        retoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
