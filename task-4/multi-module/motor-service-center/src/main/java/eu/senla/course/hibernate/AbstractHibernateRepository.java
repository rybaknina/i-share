package eu.senla.course.hibernate;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.api.repository.IRepository;
import eu.senla.course.exception.RepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractHibernateRepository<T extends IEntity> implements IRepository<T> {

    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    AbstractHibernateRepository() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void add(T t) throws RepositoryException {
        if (t == null) {
            throw new RepositoryException("Entity is not exist");
        }
        findEntity(t);
    }

    private void findEntity(T t) {
        T findEntity = entityManager.find(entityClass, t.getId());
        if (findEntity != null) {
            entityManager.merge(t);
        } else {
            entityManager.persist(t);
        }
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.getReference(entityClass, id));
    }

    @Override
    public void update(T t) throws RepositoryException {
        entityManager.merge(t);
    }

    @Override
    public T getById(int id) {
        if (id > 0) {
            return entityManager.find(entityClass, id);
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
        Root<T> rootEntry = criteriaQuery.from(entityClass);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);

        return entityManager.createQuery(all).getResultList();
    }

    @Override
    public void setAll(List<T> ts) {
        for (T t: ts) {
            findEntity(t);
        }
    }

    @Override
    public void addAll(List<T> ts) {
        for (T t: ts) {
            findEntity(t);
            //entityManager.merge(t);
        }
    }
}
