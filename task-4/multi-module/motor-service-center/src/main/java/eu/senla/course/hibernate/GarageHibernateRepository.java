package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.entity.*;
import eu.senla.course.exception.RepositoryException;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Component("garageHibernateRepository")
public class GarageHibernateRepository implements IGarageRepository {

    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    public GarageHibernateRepository() {
    }

    public void add(Garage garage) throws RepositoryException {
        if (garage == null) {
            throw new RepositoryException("Entity is not exist");
        }
        findEntity(garage);
    }

    public void update(Garage garage) throws RepositoryException {
        entityManager.merge(garage);
    }

    public Garage getById(int id) {
        if (id > 0) {
            return entityManager.find(Garage.class, id);
        }
        return null;
    }
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

        entityManager.createQuery(criteriaUpdate).executeUpdate();
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    private <Y> CriteriaUpdate<Mechanic> setPathToNull(CriteriaBuilderImpl criteriaBuilder, CriteriaUpdate<Mechanic> criteriaUpdate, Path<Y> value) {
        return criteriaUpdate.set(value, new LiteralExpression<>(criteriaBuilder, (Y) null));
    }

    public void setAll(List<Garage> garageList) {
        for (Garage garage: garageList) {
            findEntity(garage);
        }
    }

    public void addAll(List<Garage> garageList) {
        for (Garage garage: garageList) {
            entityManager.merge(garage);
        }
    }
    private void findEntity(Garage garage) {
        Garage findEntity = entityManager.find(Garage.class, garage.getId());
        if (findEntity != null) {
            entityManager.merge(garage);
        } else {
            entityManager.persist(garage);
        }
    }
}
