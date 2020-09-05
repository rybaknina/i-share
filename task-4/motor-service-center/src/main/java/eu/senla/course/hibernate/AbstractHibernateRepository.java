package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IRepository;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.JPAUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractHibernateRepository<T> implements IRepository<T> {

    private EntityManager entityManager = JPAUtility.getEntityManager();

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
        entityManager.getTransaction().begin();
        entityManager.merge(t);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.getReference(entityClass, id));
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(T t) throws RepositoryException {
        entityManager.getTransaction().begin();
        entityManager.merge(t);
        entityManager.getTransaction().commit();
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

    }

    @Override
    public void addAll(List<T> ts) {
        entityManager.getTransaction().begin();
        for (T t: ts) {
            entityManager.merge(t);
        }
        entityManager.getTransaction().commit();
    }
}
