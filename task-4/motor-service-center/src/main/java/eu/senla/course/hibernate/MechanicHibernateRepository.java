package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.enums.sql.SqlMechanic;
import eu.senla.course.util.JPAUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import java.util.List;

@Repository
public class MechanicHibernateRepository extends AbstractHibernateRepository<Mechanic> implements IMechanicRepository {
    private EntityManager entityManager = JPAUtility.getEntityManager();

    @Override
    public void setAll(List<Mechanic> mechanics) {
        CriteriaDelete<Mechanic> criteriaDelete = entityManager.getCriteriaBuilder().createCriteriaDelete(Mechanic.class);
        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.createNativeQuery(SqlMechanic.RESET.getName()).executeUpdate();
        for (Mechanic mechanic: mechanics) {
            entityManager.merge(mechanic);
        }
        entityManager.getTransaction().commit();
    }
}
