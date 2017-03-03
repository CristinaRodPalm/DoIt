package com.doitteam.doit.repository;

import com.doitteam.doit.domain.UserExt;
import com.doitteam.doit.domain.UserExt_;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
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

    private CriteriaBuilder builder;

    private CriteriaQuery<UserExt> userExtCriteriaQuery;

    private Root<UserExt> userExtRoot;

    @PostConstruct
    public void initCriteria(){
        builder = entityManager.getCriteriaBuilder();
        userExtCriteriaQuery = builder.createQuery(UserExt.class);
        userExtRoot = userExtCriteriaQuery.from(UserExt.class);
    }

    public List<UserExt> filterUserextDefinitions(Map<String, Object> parameters) {

        filterByUserExt(parameters);

        return entityManager.createQuery(userExtCriteriaQuery).getResultList();
    }

    private void filterByUserExt(Map<String, Object> parameters) {

        String searchTelf = (String) parameters.get("telefono");
        //userExtCriteria.add(Restrictions.ilike("city", searchName, MatchMode.ANYWHERE));

        userExtCriteriaQuery.select(userExtRoot);
        userExtCriteriaQuery.where(builder.like(userExtRoot.get(UserExt_.telefono),
            "%"+searchTelf+"%"));
    }
}
