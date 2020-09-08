package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.entity.Tool;

@Repository
public class ToolHibernateRepository extends AbstractHibernateRepository<Tool> implements IToolRepository {
}
