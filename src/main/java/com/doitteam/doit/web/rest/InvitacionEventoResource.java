package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Evento;
import com.doitteam.doit.domain.InvitacionEvento;
import com.doitteam.doit.domain.User;
import com.doitteam.doit.repository.EventoRepository;
import com.doitteam.doit.repository.InvitacionEventoRepository;
import com.doitteam.doit.repository.UserRepository;
import com.doitteam.doit.security.SecurityUtils;
import com.doitteam.doit.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing InvitacionEvento.
 */
@RestController
@RequestMapping("/api")
public class InvitacionEventoResource {

    private final Logger log = LoggerFactory.getLogger(InvitacionEventoResource.class);

    private static final String ENTITY_NAME = "invitacionEvento";

    private final InvitacionEventoRepository invitacionEventoRepository;
    private final UserRepository userRepository;
    private final EventoRepository eventoRepository;

    public InvitacionEventoResource(InvitacionEventoRepository invitacionEventoRepository, UserRepository userRepository, EventoRepository eventoRepository) {
        this.invitacionEventoRepository = invitacionEventoRepository;
        this.userRepository = userRepository;
        this.eventoRepository = eventoRepository;
    }

    @PostMapping("/invitacion-eventos")
    @Timed
    public ResponseEntity<InvitacionEvento> createInvitacionEvento(@Valid @RequestBody InvitacionEvento invitacionEvento) throws URISyntaxException {
       log.debug("REST request to save InvitacionEvento : {}", invitacionEvento);
        if (invitacionEvento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new invitacionEvento cannot already have an ID")).body(null);
        }
        InvitacionEvento result = invitacionEventoRepository.save(invitacionEvento);
        return ResponseEntity.created(new URI("/api/invitacion-eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/invitacion-eventos")
    @Timed
    public ResponseEntity<InvitacionEvento> updateInvitacionEvento(@Valid @RequestBody InvitacionEvento invitacionEvento) throws URISyntaxException {
        log.debug("REST request to update InvitacionEvento : {}", invitacionEvento);
        if (invitacionEvento.getId() == null) {
            return createInvitacionEvento(invitacionEvento);
        }
        InvitacionEvento result = invitacionEventoRepository.save(invitacionEvento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invitacionEvento.getId().toString()))
            .body(result);
    }

    @GetMapping("/invitacion-eventos")
    @Timed
    public List<InvitacionEvento> getAllInvitacionEventos() {
        log.debug("REST request to get all InvitacionEventos");
        List<InvitacionEvento> invitacionEventos = invitacionEventoRepository.findAll();
        return invitacionEventos;
    }

    @GetMapping("/invitacion-eventos/{id}")
    @Timed
    public ResponseEntity<InvitacionEvento> getInvitacionEvento(@PathVariable Long id) {
        log.debug("REST request to get InvitacionEvento : {}", id);
        InvitacionEvento invitacionEvento = invitacionEventoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invitacionEvento));
    }

    @DeleteMapping("/invitacion-eventos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvitacionEvento(@PathVariable Long id) {
        log.debug("REST request to delete InvitacionEvento : {}", id);
        invitacionEventoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    //para que el usuario logeado se apunte a un evento de la lista
    @PostMapping("/invitacion-eventos/{id}/apuntarse")
    @Timed
    public ResponseEntity<InvitacionEvento> apuntarse(@PathVariable Long id) throws URISyntaxException {
        // EL ID ES DEL EVENTO
        //tenemos el usuario logeado
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        //tenemos el evento clicado por el usuario logeado
        Evento evento = eventoRepository.findOne(id);
        List<InvitacionEvento> inviEventoList = invitacionEventoRepository.findEventosSigned(userLogin.getId());

        for(InvitacionEvento inviEvento: inviEventoList){
            if(id.equals(inviEvento.getInvitado().getId()) || id.equals(inviEvento.getMiembroEvento().getId())){
                return ResponseEntity.badRequest().headers(HeaderUtil.
                    createFailureAlert(ENTITY_NAME, "invitationexists", "Ya te has apuntado a este evento!")).
                    body(null);
            }
        }
        //control de error
        //comprobar que la id_evento no sea null
        //comprobar que la invitacion del evento no sea null

        InvitacionEvento invitacion = new InvitacionEvento();
        invitacion.setHoraInvitacion(ZonedDateTime.now());
        invitacion.setMiembroEvento(userLogin);
        invitacion.setEvento(evento);
        invitacion.setInvitado(userLogin);
        invitacion.setHoraResolucion(ZonedDateTime.now());
        invitacion.setResolucion(true);

        InvitacionEvento result = invitacionEventoRepository.save(invitacion);
        return ResponseEntity.created(new URI("/api/invitacion-eventos/apuntarse" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/invitacion-eventos/eventosUsuarioApuntado")
    @Timed
    public List<InvitacionEvento> getEventosUsuario() {
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<InvitacionEvento> invitacionEvento = invitacionEventoRepository.findEventosSigned(userLogin.getId());
        //System.out.println(invitacionEvento);
        return invitacionEvento;
       //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invitacionEvento));
    }

    @GetMapping("/invitacion-eventos/eventosUsuarioNoApuntado")
    @Timed
    public List<Evento> getEventosNoUsuario(){
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<Evento> eventos = invitacionEventoRepository.findEventosNotSigned(userLogin.getId());
        return eventos;
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invitacionEventos));
    }


    //un usuario invita a sus amigos al evento que este se ha apuntado
    @PostMapping("/invitacion-eventos/invitarAmigos/{idEvento}/{idInvitados}")
    @Timed
    public List<InvitacionEvento> crearInvitacionsAmigos(@PathVariable Long idEvento, @PathVariable(value = "idInvitados", required = false) String idInvitados){
        String id[] = idInvitados.split(",");
        List<User> invitados = new ArrayList<>();

        for(String idUsuario: id){
            invitados.add(userRepository.findOne(Long.parseLong(idUsuario)));
        }

        List<InvitacionEvento> invitaditos = invitados.stream().map(invitado -> {
            InvitacionEvento invitacion = new InvitacionEvento();
            invitacion.setMiembroEvento(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
            invitacion.setInvitado(invitado);
            invitacion.setEvento(eventoRepository.findOne(idEvento));
            invitacion.setHoraInvitacion(ZonedDateTime.now());
            return invitacionEventoRepository.save(invitacion);
        }).collect(Collectors.toList());

        return invitaditos;
    }

    //invitaciones pendientes del usuario logeado
    @GetMapping("/invitacion-eventos/invitacionesPendientes")
    @Timed
    public List<InvitacionEvento> getInvitacionesPendientes(){
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<InvitacionEvento> invitacionEventos = invitacionEventoRepository.findPendingInvitations(userLogin.getId());
        return invitacionEventos;
    }

    //aceptar invitacion a evento
    @PutMapping("invitacion-eventos/{id}/accept")
    @Timed
    public ResponseEntity<InvitacionEvento> accept(@PathVariable Long id) {
        log.debug("REST request to accept Invitation : {}", id);
        if (id == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert
                (ENTITY_NAME, "noexists", "La invitacion no existe")).body(null);
        }
        InvitacionEvento invitacionEvento = invitacionEventoRepository.findOne(id);
        invitacionEvento.setHoraResolucion(ZonedDateTime.now());
        invitacionEvento.setResolucion(true);
        InvitacionEvento result = invitacionEventoRepository.save(invitacionEvento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invitacionEvento.getId().toString()))
            .body(result);
    }

    //rechazar invitacion a evento
    @PutMapping("invitacion-eventos/{id}/deny")
    @Timed
    public ResponseEntity<InvitacionEvento> deny(@PathVariable Long id) {
        log.debug("REST request to deny Invitation : {}", id);
        if (id == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert
                (ENTITY_NAME, "noexists", "La invitacion no existe")).body(null);
        }
        InvitacionEvento invitacionEvento = invitacionEventoRepository.findOne(id);
        invitacionEvento.setHoraResolucion(ZonedDateTime.now());
        invitacionEvento.setResolucion(false);
        InvitacionEvento result = invitacionEventoRepository.save(invitacionEvento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invitacionEvento.getId().toString()))
            .body(result);
    }
}
