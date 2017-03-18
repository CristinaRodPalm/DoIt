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
        String firstName = (String) parameters.get("firstName");
        String lastName = (String) parameters.get("lastName");
        String email = (String) parameters.get("email");


        if(login != null) filterByLogin(parameters, userExtCriteriaQuery, builder);
        if(telefono != null) filterByTlf(parameters, userExtCriteriaQuery, userExtRoot, builder);
        if(firstName != null) filterByFirstName(parameters, userExtCriteriaQuery, builder);
        if(lastName != null) filterByLastName(parameters, userExtCriteriaQuery, builder);
        if(email != null) filterByEmail(parameters, userExtCriteriaQuery, builder);
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

    private void filterByFirstName(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, CriteriaBuilder builder){
        String firstName = (String) parameters.get("firstName");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.equal(user.get("firstName"), firstName)).distinct(true);
    }

    private void filterByLastName(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, CriteriaBuilder builder){
        String lastName = (String) parameters.get("lastName");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.equal(user.get("lastName"), lastName)).distinct(true);
    }

    private void filterByEmail(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, CriteriaBuilder builder){
        String email = (String) parameters.get("email");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.equal(user.get("email"), email)).distinct(true);
    }
}
