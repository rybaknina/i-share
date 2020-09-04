package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IRepository;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractHibernateRepository<T> implements IRepository<T> {

    private final Class<T> entityClass;
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    AbstractHibernateRepository() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void add(T t) throws RepositoryException {
        if (t == null) {
            throw new RepositoryException("Entity is not exist");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T t) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(T t) throws RepositoryException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public T getById(int id) {
        if (id != 0) {
            Session session = sessionFactory.openSession();
            T entity = session.get(entityClass, id);
            session.close();
            return entity;
        }
        return null;
    }

    @Override
    public List<T> getAll() {

        Session session = sessionFactory.openSession();
        CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(entityClass);
        Root<T> rootEntry = criteriaQuery.from(entityClass);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        List<T> list = session.createQuery(all).getResultList();
        session.close();

        return list;
    }

    @Override
    public void setAll(List<T> ts) {

    }

    @Override
    public void addAll(List<T> ts) {

    }
}
