package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.entity.Mechanic;

@Repository
public class MechanicHibernateRepository extends AbstractHibernateRepository<Mechanic> implements IMechanicRepository {
}
