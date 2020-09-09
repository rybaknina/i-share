package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.entity.Spot;

@Repository
public class SpotHibernateRepository extends AbstractHibernateRepository<Spot> implements ISpotRepository {
}
