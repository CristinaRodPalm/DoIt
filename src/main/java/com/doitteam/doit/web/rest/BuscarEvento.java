package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.Evento;
import com.doitteam.doit.repository.BuscarEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.transaction.Transactional;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BuscarEvento {

    @Autowired
    BuscarEventoRepository buscarEventoRepository;

    @RequestMapping(value = "/search/eventos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<Evento>> searchEvento(
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "descripcion", required = false) String descripcion,

        @RequestParam(value = "fecha", required = false) ZonedDateTime fecha,
        @RequestParam(value = "admin", required = false) String admin
    ) throws URISyntaxException {

        Map<String, Object> params = new HashMap<>();

        if (nombre != null) params.put("nombre",nombre);
        if (fecha != null) params.put("fecha", fecha);
        if (admin != null) params.put("admin", admin);
        if(descripcion!= null) params.put("descripcion", descripcion);

        List<Evento> result = buscarEventoRepository.filter(params);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
