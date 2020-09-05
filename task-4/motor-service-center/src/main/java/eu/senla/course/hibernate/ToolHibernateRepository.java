package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.sql.SqlTool;
import eu.senla.course.util.JPAUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import java.util.List;

@Repository
public class ToolHibernateRepository extends AbstractHibernateRepository<Tool> implements IToolRepository {
    private EntityManager entityManager = JPAUtility.getEntityManager();

    @Override
    public void setAll(List<Tool> tools) {
        CriteriaDelete<Tool> criteriaDelete = entityManager.getCriteriaBuilder().createCriteriaDelete(Tool.class);
        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.createNativeQuery(SqlTool.RESET.getName()).executeUpdate();
        for (Tool tool : tools) {
            entityManager.merge(tool);
        }
        entityManager.getTransaction().commit();
    }
}
