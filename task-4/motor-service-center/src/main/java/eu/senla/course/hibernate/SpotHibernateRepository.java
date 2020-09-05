package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.sql.SqlSpot;
import eu.senla.course.util.JPAUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import java.util.List;

@Repository
public class SpotHibernateRepository extends AbstractHibernateRepository<Spot> implements ISpotRepository {
    private EntityManager entityManager = JPAUtility.getEntityManager();

    @Override
    public void setAll(List<Spot> spots) {
        CriteriaDelete<Spot> criteriaDelete = entityManager.getCriteriaBuilder().createCriteriaDelete(Spot.class);
        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.createNativeQuery(SqlSpot.RESET.getName()).executeUpdate();
        for (Spot spot: spots) {
            entityManager.merge(spot);
        }
        entityManager.getTransaction().commit();
    }
}
