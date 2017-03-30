package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Evento;

import com.doitteam.doit.repository.EventoRepository;
import com.doitteam.doit.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Evento.
 */
@RestController
@RequestMapping("/api")
public class EventoResource {

    private final Logger log = LoggerFactory.getLogger(EventoResource.class);

    private static final String ENTITY_NAME = "evento";

    private final EventoRepository eventoRepository;

    public EventoResource(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    /**
     * POST  /eventos : Create a new evento.
     *
     * @param evento the evento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evento, or with status 400 (Bad Request) if the evento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eventos")
    @Timed
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento) throws URISyntaxException {
        log.debug("REST request to save Evento : {}", evento);
        if (evento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new evento cannot already have an ID")).body(null);
        }
        Evento result = eventoRepository.save(evento);
        return ResponseEntity.created(new URI("/api/eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eventos : Updates an existing evento.
     *
     * @param evento the evento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evento,
     * or with status 400 (Bad Request) if the evento is not valid,
     * or with status 500 (Internal Server Error) if the evento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eventos")
    @Timed
    public ResponseEntity<Evento> updateEvento(@RequestBody Evento evento) throws URISyntaxException {
        log.debug("REST request to update Evento : {}", evento);
        if (evento.getId() == null) {
            return createEvento(evento);
        }
        Evento result = eventoRepository.save(evento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eventos : get all the eventos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eventos in body
     */
    @GetMapping("/eventos")
    @Timed
    public List<Evento> getAllEventos() {
        log.debug("REST request to get all Eventos");
        List<Evento> eventos = eventoRepository.findAll();
        return eventos;
    }

    /**
     * GET  /eventos/:id : get the "id" evento.
     *
     * @param id the id of the evento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evento, or with status 404 (Not Found)
     */
    @GetMapping("/eventos/{id}")
    @Timed
    public ResponseEntity<Evento> getEvento(@PathVariable Long id) {
        log.debug("REST request to get Evento : {}", id);
        Evento evento = eventoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(evento));
    }

    /**
     * DELETE  /eventos/:id : delete the "id" evento.
     *
     * @param id the id of the evento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eventos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        log.debug("REST request to delete Evento : {}", id);
        eventoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /*
    @GetMapping("/eventos/{id}")
    @Timed
    public ResponseEntity<String> getDireccionEventoByLatLong(@PathVariable Long id){
        log.debug("REST Request to get Direccion from Evento: {}", id);
        String urlMaps= "http://maps.googleapis.com/maps/api/geocode/json?latlng="+
            eventoRepository.findOne(id).getLatitud()+","+eventoRepository.findOne(id).getLongitud();;
        log.debug(urlMaps);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(ENTITY_NAME, urlMaps)).body(urlMaps);
    }
    @GetMapping("/eventos/{id}")
    @Timed
    public ResponseEntity<String> getLatLongEventoByDireccion(@PathVariable Long id){
        log.debug("REST Request to get Latitude and Longitude from Evento: {}", id);
        String direccion = eventoRepository.findOne(id).getDireccion().replace(' ', '+');
        String urlMaps= "https://maps.googleapis.com/maps/api/geocode/json?address="+direccion;
        //log.debug(direccion);
        log.debug(urlMaps);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(ENTITY_NAME, urlMaps)).body(urlMaps);
    }
    */
}
