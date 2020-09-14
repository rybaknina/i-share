package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.entity.Mechanic;
import org.springframework.stereotype.Component;

@Component
public class MechanicHibernateRepository extends AbstractHibernateRepository<Mechanic> implements IMechanicRepository {
}
