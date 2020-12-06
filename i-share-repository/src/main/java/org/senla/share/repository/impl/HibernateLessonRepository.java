package org.senla.share.repository.impl;

import org.senla.share.criteria.SearchConsumer;
import org.senla.share.criteria.SearchCriteria;
import org.senla.share.entity.Lesson;
import org.senla.share.repository.LessonRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("lessonRepository")
public class HibernateLessonRepository extends AbstractHibernateRepository<Lesson> implements LessonRepository {
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    public HibernateLessonRepository() {
        super(Lesson.class);
    }

    @Override
    public List<Lesson> search(List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Lesson> query = builder.createQuery(Lesson.class);
        final Root r = query.from(Lesson.class);

        Predicate predicate = builder.conjunction();
        SearchConsumer searchConsumer = new SearchConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Set<Lesson> lessonsByCourse(int id) {
        Set<Lesson> lessonSet = new HashSet<>();
        if (id > 0) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Lesson> criteria = builder.createQuery(Lesson.class);
            Root<Lesson> rootEntry = criteria.from(Lesson.class);
            TypedQuery<Lesson> query = entityManager.createQuery(
                    criteria.select(rootEntry)
                            .where(builder.equal(rootEntry.get("course").get("id"), id)));
            try {
                lessonSet = new HashSet<>(query.getResultList());
            } catch (NoResultException e) {
                super.logger.warn("No Result Exception " + e.getMessage());
                return lessonSet;
            }
        }
        return lessonSet;
    }
}
