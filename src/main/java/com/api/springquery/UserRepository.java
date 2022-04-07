package com.api.springquery;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<AppUser> searchUser(List<SearchCriteria> params) {

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<AppUser> query = criteriaBuilder.createQuery(AppUser.class);

        Root r = query.from(AppUser.class);

        Predicate predicate = criteriaBuilder.conjunction();

        QueryCriteriaClient searchConsumer = new QueryCriteriaClient(predicate, criteriaBuilder, r);
        params.stream().forEach(searchConsumer);

        predicate = searchConsumer.getPredicate();
        query.where(predicate); List<AppUser> result = manager.createQuery(query).getResultList();

        return result;
    }

    @Override
    public void save(AppUser entity) {
        manager.persist(entity);
    }
}
