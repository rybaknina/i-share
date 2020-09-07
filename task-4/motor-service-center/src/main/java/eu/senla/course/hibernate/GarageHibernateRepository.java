package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.entity.*;
import eu.senla.course.util.JPAUtility;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class GarageHibernateRepository extends AbstractHibernateRepository<Garage> implements IGarageRepository {
    private EntityManager entityManager = JPAUtility.getEntityManager();

    @Override
    public List<Garage> getAll() {
        CriteriaQuery<Garage> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Garage.class);
        Root<Garage> rootEntry = criteriaQuery.from(Garage.class);
        ListJoin<Garage, Spot> spotJoin = rootEntry.join(Garage_.spots, JoinType.INNER);
        CriteriaQuery<Garage> all = criteriaQuery.select(rootEntry).distinct(true);

        List<Garage> garages = entityManager.createQuery(all).getResultList();
        return garages;
    }

    @Override
    public void delete(int id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Garage> criteriaDelete = criteriaBuilder.createCriteriaDelete(Garage.class);
        Root<Garage> garageRoot = criteriaDelete.from(Garage.class);
        criteriaDelete.where(criteriaBuilder.equal(garageRoot.get(Garage_.id), id));

        CriteriaUpdate<Mechanic> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Mechanic.class);
        Root<Mechanic> mechanicRoot = criteriaUpdate.from(Mechanic.class);
        setPathToNull((CriteriaBuilderImpl) criteriaBuilder, criteriaUpdate, mechanicRoot.get(Mechanic_.garage));
        criteriaUpdate.where(criteriaBuilder.equal(mechanicRoot.get(Mechanic_.garage).get("id"), id));

        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaUpdate).executeUpdate();
        entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.getTransaction().commit();
    }

    private <Y> CriteriaUpdate<Mechanic> setPathToNull(CriteriaBuilderImpl criteriaBuilder, CriteriaUpdate<Mechanic> criteriaUpdate, Path<Y> value) {
        return criteriaUpdate.set(value, new LiteralExpression<>(criteriaBuilder, (Y) null));
    }
}
