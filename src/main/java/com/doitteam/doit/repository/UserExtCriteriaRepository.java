package com.doitteam.doit.repository;

import com.doitteam.doit.domain.User;
import com.doitteam.doit.domain.UserExt;
import com.doitteam.doit.domain.UserExt_;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Repository
public class UserExtCriteriaRepository {

    @PersistenceContext
    EntityManager entityManager;


    public List<UserExt> filterUserExtDefinitions(Map<String, Object> parameters) {
        // donde se inicia la query
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserExt> userExtCriteriaQuery = builder.createQuery(UserExt.class);
        Root<UserExt> userExtRoot = userExtCriteriaQuery.from(UserExt.class);

        filterByUserExt(parameters, userExtCriteriaQuery, userExtRoot, builder);

        return entityManager.createQuery(userExtCriteriaQuery).getResultList();
    }

    private void filterByUserExt(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, Root<UserExt> userExtRoot, CriteriaBuilder builder) {
        userExtCriteriaQuery.select(userExtRoot);

        String telefono = (String) parameters.get("telefono");
        String login = (String) parameters.get("login");
        /*String firstName = (String) parameters.get("firstName");
        String lastName = (String) parameters.get("lastName");
        String email = (String) parameters.get("email");*/

        if(telefono != null) filterByTlf(parameters, userExtCriteriaQuery, userExtRoot, builder);
        if(login != null) filterByLogin(parameters, userExtCriteriaQuery, builder);
        /*if(firstName != null) filterByFirstName(parameters);
        if(lastName != null) filterByLastName(parameters);
        if(email != null) filterByEmail(parameters);*/
    }
    private void filterByLogin(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, CriteriaBuilder builder){
        String searchLogin = (String) parameters.get("login");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.equal(user.get("login"), searchLogin)).distinct(true);
    }

    private void filterByTlf(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, Root<UserExt> userExtRoot, CriteriaBuilder builder){
        String searchTelf = (String) parameters.get("telefono");
        userExtCriteriaQuery.where(builder.like(userExtRoot.get(UserExt_.telefono),
            "%"+searchTelf+"%"));
    }













/*
    private void filterByFirstName(Map<String, Object> parameters){
        String firstName = (String) parameters.get("firstName");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserExt> query = cb.createQuery(UserExt.class);
        Root<User> user = query.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        query.select(userExt).where(cb.like(user.get("firstName"), "%"+firstName+"%"));
    }

    private void filterByLastName(Map<String, Object> parameters){
        String lastName = (String) parameters.get("lastName");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserExt> query = cb.createQuery(UserExt.class);
        Root<User> user = query.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        query.select(userExt).where(cb.like(user.get("lastName"), "%"+lastName+"%"));
    }

    private void filterByEmail(Map<String, Object> parameters){
        String email = (String) parameters.get("email");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserExt> query = cb.createQuery(UserExt.class);
        Root<User> user = query.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        query.select(userExt).where(cb.like(user.get("email"), "%"+email+"%"));
    }
*/
}
