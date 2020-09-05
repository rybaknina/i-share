package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.sql.SqlOrder;
import eu.senla.course.util.JPAUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import java.util.List;

@Repository
public class OrderHibernateRepository extends AbstractHibernateRepository<Order> implements IOrderRepository {
    private EntityManager entityManager = JPAUtility.getEntityManager();

    @Override
    public void setAll(List<Order> orders) {
        CriteriaDelete<Order> criteriaDelete = entityManager.getCriteriaBuilder().createCriteriaDelete(Order.class);
        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.createNativeQuery(SqlOrder.RESET.getName()).executeUpdate();
        for (Order order: orders) {
            entityManager.merge(order);
        }
        entityManager.getTransaction().commit();
    }
}
