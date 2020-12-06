package org.senla.share.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.entity.AbstractEntity;
import org.senla.share.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class AbstractHibernateRepository<E extends AbstractEntity> implements GenericRepository<E> {

    private final Class<E> entityClass;
    protected Logger logger = LogManager.getLogger(getClass());
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    public AbstractHibernateRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<E> save(E entity) {
        Optional<E> newEntity = Optional.empty();
        if (entity != null) {
            newEntity = findEntity(entity);
            logger.info("Saved entity " + entity);
        }
        return newEntity;
    }

    private Optional<E> findEntity(E entity) {
        E findEntity = entityManager.find(entityClass, entity.getId());
        if (findEntity != null) {
            entity = entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public void delete(int id) {
        if (getById(id).isPresent()) {
            entityManager.remove(entityManager.getReference(entityClass, id));
        }
    }

    @Override
    public Optional<E> update(E entity) {
        E findEntity = entityManager.find(entityClass, entity.getId());
        if (findEntity != null) {
            entityManager.merge(entity);
        } else {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(entityClass, entity.getId()));
    }

    @Override
    public Optional<E> getById(int id) {
        if (id > 0) {
            return Optional.ofNullable(entityManager.find(entityClass, id));
        }
        return Optional.empty();
    }

    @Override
    public List<E> getAll() {
        CriteriaQuery<E> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
        Root<E> rootEntry = criteriaQuery.from(entityClass);
        CriteriaQuery<E> all = criteriaQuery.select(rootEntry);
        return entityManager.createQuery(all).getResultList();
    }
}
