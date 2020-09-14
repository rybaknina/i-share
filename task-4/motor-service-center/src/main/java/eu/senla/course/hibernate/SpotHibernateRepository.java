package eu.senla.course.hibernate;

import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.entity.Spot;
import org.springframework.stereotype.Component;

@Component
public class SpotHibernateRepository extends AbstractHibernateRepository<Spot> implements ISpotRepository {
}
