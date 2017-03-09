package com.doitteam.doit.repository;

import com.doitteam.doit.domain.UserExt;
import com.doitteam.doit.domain.UserExt_;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    public void initCriteria(){
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
        if(telefono != null) {
            filterByTlf(parameters, userExtCriteriaQuery);
        }
    }

    private void filterByTlf(Map<String, Object> parameters, CriteriaQuery<UserExt> userExtCriteriaQuery){
        String searchTelf = (String) parameters.get("telefono");
        userExtCriteriaQuery.where(builder.like(userExtRoot.get(UserExt_.telefono),
            "%"+searchTelf+"%"));
    }

}
