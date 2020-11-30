package by.ryni.share;

import by.ryni.share.entity.AbstractEntity;
import by.ryni.share.exception.RepositoryException;
import by.ryni.share.api.GenericRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class AbstractHibernateRepository<E extends AbstractEntity> implements GenericRepository<E> {

    protected Logger logger = LogManager.getLogger(getClass());

    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    private final Class<E> entityClass;
    public AbstractHibernateRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<E> save(E entity) throws RepositoryException {
        if (entity == null) {
            throw new RepositoryException("Entity does not exist");
        }
        Optional<E> saveEntity = findEntity(entity);
        logger.info("Saved entity " + entity);
        return saveEntity;
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
    public void delete(int id) throws RepositoryException {
        if (id <= 0) {
            throw new RepositoryException("Illegal argument " + id);
        }
        entityManager.remove(entityManager.getReference(entityClass, id));
    }

    @Override
    public void update(E entity) throws RepositoryException {
        E findEntity = entityManager.find(entityClass, entity.getId());
        if (findEntity == null) {
            throw new RepositoryException("Entity does not exist");
        }
        entityManager.merge(entity);
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
