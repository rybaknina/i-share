package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IUserRepository;
import eu.senla.course.entity.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Optional;

@Component("userHibernateRepository")
public class UserHibernateRepository extends AbstractHibernateRepository<User> implements IUserRepository {

    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        if (!username.isEmpty()) {
           CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> rootEntry = criteria.from(User.class);
            ListJoin<User, Role> roleJoin = rootEntry.join(User_.roles, JoinType.INNER);
            ListJoin<Role, Permission> permissionJoin = roleJoin.join(Role_.permissions, JoinType.INNER);
            TypedQuery<User> query = entityManager.createQuery(criteria.select(rootEntry).where(builder.equal(rootEntry.get("username"), username)));
            try {
                user = query.getSingleResult();
            } catch (NoResultException e) {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(user);
    }
}
