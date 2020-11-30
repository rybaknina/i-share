package by.ryni.share;

import by.ryni.share.api.CourseRepository;
import by.ryni.share.criteria.SearchConsumer;
import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.entity.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("courseRepository")
public class HibernateCourseRepository  extends AbstractHibernateRepository<Course> implements CourseRepository {
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    public HibernateCourseRepository() {
        super(Course.class);
    }

    @Override
    public List<Course> search(List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Course> query = builder.createQuery(Course.class);
        final Root r = query.from(Course.class);

        Predicate predicate = builder.conjunction();
        SearchConsumer searchConsumer = new SearchConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public int availableSpace(int id) {
        int space = 0;
        if (id > 0) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Course> criteria = builder.createQuery(Course.class);
            Root<Course> rootEntry = criteria.from(Course.class);
            TypedQuery<Course> query = entityManager.createQuery(criteria.select(rootEntry).where(builder.equal(rootEntry.get("id"), id)));
            try {
                if (query.getSingleResult() != null) {
                    Course course = query.getSingleResult();
                    space = course.getLimitMembers() - course.getMembers().size();
                }
            } catch (NoResultException e) {
                super.logger.warn("No Result Exception " + e.getMessage());
                return 0;
            }
            return space;
        }
        return 0;
    }
}
