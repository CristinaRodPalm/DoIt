package com.doitteam.doit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.doitteam.doit.domain.UserExt;
import com.doitteam.doit.repository.UserExtCriteriaRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SearchResource {

    @Autowired
    UserExtCriteriaRepository userExtCriteriaRepository;

    @RequestMapping(value = "/search/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<UserExt>> searchUsers(
        @RequestParam(value = "telefono", required = false) String telefono,
        @RequestParam(value = "login", required = false) String login,
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "email", required = false) String email

    ) throws URISyntaxException {

        Map<String, Object> params = new HashMap<>();

        if (telefono != null) params.put("telefono",telefono);
        if (login != null) params.put("login", login);
        if (firstName != null) params.put("firstName", firstName);
        if (lastName != null) params.put("lastName", lastName);
        if (email != null) params.put("email", email);

        List<UserExt> result = userExtCriteriaRepository.filterByDefinitions(params);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
