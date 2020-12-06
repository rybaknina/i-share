package org.senla.share.repository.impl;

import org.senla.share.entity.Schedule;
import org.senla.share.entity.Schedule_;
import org.senla.share.repository.ScheduleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

@Repository("scheduleRepository")
public class HibernateScheduleRepository extends AbstractHibernateRepository<Schedule> implements ScheduleRepository {
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    public HibernateScheduleRepository() {
        super(Schedule.class);
    }

    public Set<Schedule> scheduleByLesson(int id) {
        Set<Schedule> scheduleSet = new HashSet<>();
        if (id > 0) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Schedule> criteria = builder.createQuery(Schedule.class);
            Root<Schedule> rootEntry = criteria.from(Schedule.class);
            criteria.orderBy(builder.asc(rootEntry.get(Schedule_.startDate)));
            TypedQuery<Schedule> query = entityManager.createQuery(
                    criteria.select(rootEntry)
                            .where(builder.equal(rootEntry.get("lesson").get("id"), id)));
            try {
                scheduleSet = new HashSet<>(query.getResultList());
            } catch (NoResultException e) {
                super.logger.warn("No Result Exception " + e.getMessage());
                return scheduleSet;
            }
        }
        return scheduleSet;
    }
}
