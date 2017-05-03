package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Amistad;
import com.doitteam.doit.domain.User;
import com.doitteam.doit.domain.UserExt;
import com.doitteam.doit.repository.AmistadRepository;
import com.doitteam.doit.repository.UserExtRepository;
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
import java.util.stream.Collectors;

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
    private final UserExtRepository userExtRepository;

    public AmistadResource(AmistadRepository amistadRepository, UserRepository userRepository, UserExtRepository userExtRepository) {
        this.amistadRepository = amistadRepository;
        this.userRepository = userRepository;
        this.userExtRepository = userExtRepository;
    }

    @PostMapping("/amistads")
    @Timed
    public ResponseEntity<Amistad> createAmistad(@Valid @RequestBody Amistad amistad) throws URISyntaxException {
        System.out.println(amistad);
        log.debug("REST request to save Amistad : {}", amistad);
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<Amistad> amigos = amistadRepository.findAllFriends(userLogin.getId());
        for (Amistad amigo: amigos) {
            if(amistad.getReceptor().equals(amigo.getReceptor()) || amistad.getReceptor().equals(amigo.getEmisor())){
                return ResponseEntity.badRequest().headers(HeaderUtil.
                    createFailureAlert(ENTITY_NAME, "friendexists", "Este usuario ya es tu amigo!")).
                    body(null);
            }
        }
        if (amistad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new amistad cannot already have an ID")).body(null);
        }
        amistad.setTimeStamp(ZonedDateTime.now());
        amistad.setEmisor(userLogin);
        Amistad result = amistadRepository.save(amistad);
        return ResponseEntity.created(new URI("/api/amistads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    // Enviar solicitud amistad desde buscar amigo
    @PostMapping("/amistad/{id}/emisor")
    @Timed
    public ResponseEntity<Amistad> sendFriendRequest(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to save Amistad : {}");
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        User userReceptor = userRepository.findOne(id);
        List<Amistad> amigos = amistadRepository.findAllFriends(userLogin.getId());
        for (Amistad amigo: amigos) {
            if(id.equals(amigo.getReceptor().getId()) || id.equals(amigo.getEmisor().getId())){
                return ResponseEntity.badRequest().headers(HeaderUtil.
                    createFailureAlert(ENTITY_NAME, "friendexists", "Este usuario ya es tu amigo!")).
                    body(null);
            }
        }
        Amistad result = amistadRepository.save(new Amistad(ZonedDateTime.now(), userLogin, userReceptor));
        return ResponseEntity.created(new URI("/api/amistads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/amistad/amigosSolicitudes")
    public List<Amistad> getAmigosSolicitudes(){
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<Amistad> amigos = amistadRepository.findAmigos(userLogin.getId());
       /* for (Amistad amigo: amigos) { }
        return null;*/
       return amigos;
    }

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

    @GetMapping("/amistads")
    @Timed
    public List<Amistad> getAllAmistads() {
        log.debug("REST request to get all Amistads");
        List<Amistad> amistads = amistadRepository.findAll();
        return amistads;
    }

    @GetMapping("/amistads/{id}")
    @Timed
    public ResponseEntity<Amistad> getAmistad(@PathVariable Long id) {
        log.debug("REST request to get Amistad : {}", id);
        Amistad amistad = amistadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(amistad));
    }

    @DeleteMapping("/amistads/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmistad(@PathVariable Long id) {
        log.debug("REST request to delete Amistad : {}", id);
        amistadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PutMapping("/amistads/{id}/accept")
    @Timed
    public ResponseEntity<Amistad> accept(@PathVariable Long id) {
        log.debug("REST request to update Amistad : {}", id);
        if (id == null) {
            return ResponseEntity.badRequest().
                headers(HeaderUtil.createFailureAlert
                    (ENTITY_NAME, "noexists", "La amistad no existe")).body(null);
        }
        Amistad amistad = amistadRepository.findById(id);
        amistad.setHoraRespuesta(ZonedDateTime.now());
        amistad.setAceptada(true);
        Amistad result = amistadRepository.save(amistad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amistad.getId().toString()))
            .body(result);
    }

    @PutMapping("/amistads/{id}/deny")
    @Timed
    public ResponseEntity<Amistad> deny(@PathVariable Long id){
        log.debug("REST request to update Amistad : {}", id);
        if (id == null) {
            return ResponseEntity.badRequest().
                headers(HeaderUtil.createFailureAlert
                    (ENTITY_NAME, "noexists", "La amistad no existe")).body(null);

        }
        Amistad amistad = amistadRepository.findById(id);
        amistad.setHoraRespuesta(ZonedDateTime.now());
        amistad.setAceptada(false);
        Amistad result = amistadRepository.save(amistad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amistad.getId().toString()))
            .body(result);
    }

    @GetMapping("/amistades")
    @Timed
    public List<Amistad> getAllAmistadsByCurrentUser() throws URISyntaxException {
        log.debug("REST Request para obtener solicitudes de amistades por el usuario logeado", SecurityUtils.getCurrentUserLogin());
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        return amistadRepository.findByReceptorIsCurrentUser(userLogin.getId());
    }

    // GET DE AMISTADES
    @GetMapping("/amigos")
    @Timed
    public List<User> getFriends() throws URISyntaxException {
        log.debug("REST Request para obtener solicitudes de amistades por el usuario logeado", SecurityUtils.getCurrentUserLogin());
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<User> amigos = amistadRepository.findAllFriends(userLogin.getId()).
            parallelStream().
            map(amistad -> {
                if(amistad.getEmisor().equals(userLogin)){
                    return amistad.getReceptor();
                }else{
                    return amistad.getEmisor();
                }
            })
            .collect(Collectors.toList());

        return amigos;
    }

    //get solicitudes pendientes
    @GetMapping("/usersSolPendientes")
    @Timed
    public List<UserExt> getFriendsPendingRequest() throws URISyntaxException {
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<UserExt> amigos = amistadRepository.findFriendsPendingRequest(userLogin.getId()).
            parallelStream().
            map(amistad -> {
                UserExt user;
                if(amistad.getEmisor().equals(userLogin)){
                    user = userExtRepository.findByUserID(amistad.getReceptor().getId());
                }else{
                    user = userExtRepository.findByUserID(amistad.getEmisor().getId());
                }
                return user;
            })
            .collect(Collectors.toList());

        return amigos;
    }

    //get solicitudes aceptadas (amigos)
    @GetMapping("/usersSolAceptadas")
    @Timed
    public List<UserExt> getFriendsAccepted() throws URISyntaxException {
        User userLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        List<UserExt> amigos = amistadRepository.findAllFriends(userLogin.getId()).
            parallelStream().
            map(amistad -> {
                UserExt user;
                if(amistad.getEmisor().equals(userLogin)){
                    user = userExtRepository.findByUserID(amistad.getReceptor().getId());
                }else{
                    user = userExtRepository.findByUserID(amistad.getEmisor().getId());
                }
                return user;
            })
            .collect(Collectors.toList());

        return amigos;
    }
}
