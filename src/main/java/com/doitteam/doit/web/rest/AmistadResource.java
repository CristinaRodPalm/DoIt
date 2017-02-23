package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Amistad;

import com.doitteam.doit.repository.AmistadRepository;
import com.doitteam.doit.repository.UserRepository;
import com.doitteam.doit.security.SecurityUtils;
import com.doitteam.doit.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Amistad.
 */
@RestController
@RequestMapping("/api")
public class AmistadResource {

    private final Logger log = LoggerFactory.getLogger(AmistadResource.class);

    private static final String ENTITY_NAME = "amistad";

    private final AmistadRepository amistadRepository;
    private final UserRepository userRepository;

    public AmistadResource(AmistadRepository amistadRepository, UserRepository userRepository) {

        this.amistadRepository = amistadRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /amistads : Create a new amistad.
     *
     * @param amistad the amistad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amistad, or with status 400 (Bad Request) if the amistad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amistads")
    @Timed
    public ResponseEntity<Amistad> createAmistad(@Valid @RequestBody Amistad amistad) throws URISyntaxException {
        log.debug("REST request to save Amistad : {}", amistad);
        if (amistad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new amistad cannot already have an ID")).body(null);
        }
        amistad.setEmisor(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        amistad.setTimeStamp(ZonedDateTime.now());
        amistad.setAceptada(false);
        Amistad result = amistadRepository.save(amistad);
        return ResponseEntity.created(new URI("/api/amistads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amistads : Updates an existing amistad.
     *
     * @param amistad the amistad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amistad,
     * or with status 400 (Bad Request) if the amistad is not valid,
     * or with status 500 (Internal Server Error) if the amistad couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amistads")
    @Timed
    public ResponseEntity<Amistad> updateAmistad(@Valid @RequestBody Amistad amistad) throws URISyntaxException {
        log.debug("REST request to update Amistad : {}", amistad);
        if (amistad.getId() == null) {
            return createAmistad(amistad);
        }
        Amistad result = amistadRepository.save(amistad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amistad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amistads : get all the amistads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of amistads in body
     */
    @GetMapping("/amistads")
    @Timed
    public List<Amistad> getAllAmistads() {
        log.debug("REST request to get all Amistads");
        List<Amistad> amistads = amistadRepository.findAll();
        return amistads;
    }

    /**
     * GET  /amistads/:id : get the "id" amistad.
     *
     * @param id the id of the amistad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amistad, or with status 404 (Not Found)
     */
    @GetMapping("/amistads/{id}")
    @Timed
    public ResponseEntity<Amistad> getAmistad(@PathVariable Long id) {
        log.debug("REST request to get Amistad : {}", id);
        Amistad amistad = amistadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(amistad));
    }

    /**
     * DELETE  /amistads/:id : delete the "id" amistad.
     *
     * @param id the id of the amistad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amistads/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmistad(@PathVariable Long id) {
        log.debug("REST request to delete Amistad : {}", id);
        amistadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
