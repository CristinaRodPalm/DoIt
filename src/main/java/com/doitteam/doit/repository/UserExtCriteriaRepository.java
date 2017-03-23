package com.doitteam.doit.repository;

import com.doitteam.doit.domain.User;
import com.doitteam.doit.domain.UserExt;
import com.doitteam.doit.domain.UserExt_;
import com.doitteam.doit.domain.User_;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserExtCriteriaRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<UserExt> filterByDefinitions(Map<String, Object> parameters){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserExt> userExtCriteriaQuery = builder.createQuery(UserExt.class);
        Root<UserExt> userExtRoot = userExtCriteriaQuery.from(UserExt.class);

        List<Predicate> predicates = new ArrayList();

        if(parameters.get("telefono") != null){
            predicates.add(builder.like(userExtRoot.get("telefono"), (String) parameters.get("telefono")));
        }
        if(parameters.get("login") != null){
            Join<UserExt, User> userExt = userExtRoot.join("user");
            predicates.add(builder.like(userExt.get("login"), (String) parameters.get("login")));

        }

        userExtCriteriaQuery.select(userExtRoot)
            .where(predicates.toArray(new Predicate[]{}));

        return entityManager.createQuery(userExtCriteriaQuery)
            .getResultList();
    }



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

        if(login != null) filterByLogin(parameters, userExtCriteriaQuery, userExtRoot, builder);
        if(telefono != null) filterByTlf(parameters, userExtCriteriaQuery, userExtRoot, builder);
        if(firstName != null) filterByFirstName(parameters, userExtCriteriaQuery, builder);
        if(lastName != null) filterByLastName(parameters, userExtCriteriaQuery, builder);
        if(email != null) filterByEmail(parameters, userExtCriteriaQuery, builder);
    }
    // Atributo de UserExt
    private void filterByTlf(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, Root<UserExt> userExtRoot, CriteriaBuilder builder){
        String searchTelf = (String) parameters.get("telefono");
        userExtCriteriaQuery.where(builder.like(userExtRoot.get(UserExt_.telefono),
            "%"+searchTelf+"%"));
    }
    //Atributo de User
    private void filterByLogin(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, Root<UserExt> userExtRoot, CriteriaBuilder builder){
        String login = (String) parameters.get("login");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.like(user.get("login"), login)).distinct(true);
    }
    private void filterByFirstName(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, CriteriaBuilder builder){
        String firstName = (String) parameters.get("firstName");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.like(user.get("firstName"), firstName)).distinct(true);
    }
    private void filterByLastName(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, CriteriaBuilder builder){
        String lastName = (String) parameters.get("lastName");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.like(user.get("lastName"), lastName)).distinct(true);
    }
    private void filterByEmail(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery, CriteriaBuilder builder){
        String email = (String) parameters.get("email");

        Root<User> user = userExtCriteriaQuery.from(User.class);
        Join<User, UserExt> userExt = user.join("userExt");
        userExtCriteriaQuery.select(userExt).where(builder.like(user.get("email"), email)).distinct(true);
    }
    public List<User> filterTest(Map<String, Object> parameters){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<User> subRoot = subquery.from(User.class);
        subquery.select(subRoot.get(User_.id));

        List<Predicate> predicates = new ArrayList<Predicate>();

        ParameterExpression<String> login = builder.parameter(String.class);
        ParameterExpression<String> firstName = builder.parameter(String.class);
        Predicate exp1 = builder.equal(subRoot.get(User_.login), login);
        Predicate exp2 = builder.equal(subRoot.get(User_.firstName), firstName);
        Predicate and = builder.and(exp1, exp2);

        predicates.add(and);

        String log = (String) parameters.get("login");
        String first = (String) parameters.get("firstName");

        subquery.where(predicates.toArray(new Predicate[0]));
        query.where(builder.exists(subquery).not());

        return entityManager.createQuery(query)
            .setParameter(login, log)
            .setParameter(firstName, first)
            .getResultList();

    }
}
