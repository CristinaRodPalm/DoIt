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

    @Inject
    UserRepository userRepository;

    private CriteriaBuilder builder;

    private CriteriaQuery<UserExt> userExtCriteriaQuery;
    private Root<UserExt> userExtRoot;

    @PostConstruct
    public void initCriteria() {
        builder = entityManager.getCriteriaBuilder();

        userExtCriteriaQuery = builder.createQuery(UserExt.class);
        userExtRoot = userExtCriteriaQuery.from(UserExt.class);
    }

    public List<UserExt> filterUserExtDefinitions(Map<String, Object> parameters) {

        filterByUserExt(parameters);

        return entityManager.createQuery(userExtCriteriaQuery).getResultList();
    }

    private void filterByUserExt(Map<String, Object> parameters) {
        userExtCriteriaQuery.select(userExtRoot);

        String telefono = (String) parameters.get("telefono");
        String login = (String) parameters.get("login");
        if(telefono != null) {
            filterByTlf(parameters, userExtCriteriaQuery);
        }
        if(login != null){
            filterByLogin(parameters, userExtCriteriaQuery);
        }
    }
    private void filterByLogin(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery){
        String searchLogin = (String) parameters.get("login");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserExt> query = cb.createQuery(UserExt.class);
        Root<User> teacher = query.from(User.class);
        Join<User, UserExt> phones = teacher.join("userExt");
        query.select(phones).where(cb.equal(teacher.get("login"), searchLogin));

    }

    private void filterByTlf(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery){
        String searchTelf = (String) parameters.get("telefono");
        userExtCriteriaQuery.where(builder.like(userExtRoot.get(UserExt_.telefono),
            "%"+searchTelf+"%"));
    }

}
