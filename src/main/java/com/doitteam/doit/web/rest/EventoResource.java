package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Evento;
import com.doitteam.doit.repository.EventoRepository;
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
import java.time.ZonedDateTime;
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
    private final UserRepository userRepository;

    public EventoResource(EventoRepository eventoRepository, UserRepository userRepository) {
        this.eventoRepository = eventoRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/eventos")
    @Timed
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento) throws URISyntaxException {
        log.debug("REST request to save Evento : {}", evento);
        if (evento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new evento cannot already have an ID")).body(null);
        }
        evento.setHora(ZonedDateTime.now());
        evento.setAdmin(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        Evento result = eventoRepository.save(evento);
        return ResponseEntity.created(new URI("/api/eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

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

    @GetMapping("/eventos")
    @Timed
    public List<Evento> getAllEventos() {
        log.debug("REST request to get all Eventos");
        List<Evento> eventos = eventoRepository.findAll();
        return eventos;
    }

    @GetMapping("/eventos/{id}")
    @Timed
    public ResponseEntity<Evento> getEvento(@PathVariable Long id) {
        log.debug("REST request to get Evento : {}", id);
        Evento evento = eventoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(evento));
    }

    @DeleteMapping("/eventos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        log.debug("REST request to delete Evento : {}", id);
        eventoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /*@GetMapping("/eventos/next3")
    @Timed
    public List<Evento> getAllRetosOrder() {
        // para invertir Comparator.comparing(Reto::getHoraPublicacion).reversed()
        //return retoRepository.findAll().parallelStream().sorted(Comparator.comparing(Reto::getHoraPublicacion)).collect(Collectors.toList());

        // todos los eventos ordenados por fecha

    *//*List<Evento> eventos = eventoRepository.findAll().parallelStream().sorted((evento1, evento2) -> {
        System.out.println("holi");
        int offset1 = evento1.getFechaEvento().compareTo(ZonedDateTime.now());
        int offset2 = evento2.getFechaEvento().compareTo(ZonedDateTime.now());
        if(offset1 < offset2) return -1;
        else if(offset1 > offset2) return 1;
        else return 0; //son iguales
    }).collect(Collectors.toList());*//*

    List<Evento> eventos = eventoRepository.findAll().parallelStream().sorted((o1, o2) -> {

        long diff1 = ChronoUnit.DAYS.between(o1.getFechaEvento(), ZonedDateTime.now());
        long diff2 = ChronoUnit.DAYS.between(o2.getFechaEvento(), ZonedDateTime.now());
        if(diff1 > diff2) return 1;
        else if(diff2 > diff1) return -1;
        else return 0;
    }).collect(Collectors.toList());


        return null;
    }*/



}
