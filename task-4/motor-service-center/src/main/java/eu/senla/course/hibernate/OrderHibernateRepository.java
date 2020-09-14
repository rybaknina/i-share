package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderHibernateRepository extends AbstractHibernateRepository<Order> implements IOrderRepository {
}
