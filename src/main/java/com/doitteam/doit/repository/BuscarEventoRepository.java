package com.doitteam.doit.repository;

import com.doitteam.doit.domain.Evento;
import com.doitteam.doit.domain.UserExt;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 20/03/2017.
 */
@Repository
public class BuscarEventoRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Evento> filter(Map<String, Object> parameters) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evento> eventoCriteria = builder.createQuery(Evento.class);
        Root<Evento> root = eventoCriteria.from(Evento.class);

        List<Predicate> predicates = new ArrayList();

        if (parameters.get("nombre") != null) {
            predicates.add(builder.like(root.get("nombre"), (String) parameters.get("nombre")));
        }
        if (parameters.get("descripcion") != null) {
            predicates.add(builder.like(root.get("descripcion"), (String) parameters.get("descripcion")));
        }
        if (parameters.get("fecha") != null) {
            predicates.add(builder.equal(root.get("fechaEvento"), parameters.get("fecha")));
        }
        if (parameters.get("admin") != null) {
        /*
            Join<UserExt, User> userExt = userExtRoot.join("user");
            predicates.add(builder.like(userExt.get("login"), (String) parameters.get("login")));
        */
        }

        //participantes
        //maps

        eventoCriteria.select(root)
            .where(predicates.toArray(new Predicate[]{}));

        return entityManager.createQuery(eventoCriteria).getResultList();
    }

}
