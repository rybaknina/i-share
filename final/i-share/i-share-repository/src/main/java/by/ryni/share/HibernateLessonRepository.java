package by.ryni.share;

import by.ryni.share.criteria.SearchConsumer;
import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.entity.Lesson;
import by.ryni.share.api.LessonRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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
}
