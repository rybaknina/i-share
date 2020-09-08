package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.entity.Order;

@Repository
public class OrderHibernateRepository extends AbstractHibernateRepository<Order> implements IOrderRepository {
}
