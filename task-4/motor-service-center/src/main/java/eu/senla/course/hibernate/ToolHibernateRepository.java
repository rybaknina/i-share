package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.entity.Tool;
import org.springframework.stereotype.Component;

@Component("toolHibernateRepository")
public class ToolHibernateRepository extends AbstractHibernateRepository<Tool> implements IToolRepository {

}
