package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.sql.SqlOrder;
import eu.senla.course.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class OrderHibernateRepository extends AbstractHibernateRepository<Order> implements IOrderRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void setAll(List<Order> orders) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(SqlOrder.DELETE_ALL.getName()).executeUpdate();
        session.createSQLQuery(SqlOrder.RESET.getName()).executeUpdate();
        for (Order order: orders) {
            session.save(order);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addAll(List<Order> orders) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Order order: orders) {
            session.save(order);
        }
        session.getTransaction().commit();
        session.close();
    }
}
