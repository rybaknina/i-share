package by.ryni.share;

import by.ryni.share.entity.Role;
import by.ryni.share.entity.User;
import by.ryni.share.entity.User_;
import by.ryni.share.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Optional;

@Repository("userRepository")
public class HibernateUserRepository extends AbstractHibernateRepository<User> implements UserRepository {
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    public HibernateUserRepository() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        if (!username.isEmpty()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> rootEntry = criteria.from(User.class);
            SetJoin<User, Role> roleJoin = rootEntry.join(User_.roles, JoinType.INNER);
            TypedQuery<User> query = entityManager.createQuery(criteria.select(rootEntry).where(builder.equal(rootEntry.get("username"), username)));
            try {
                user = query.getSingleResult();
            } catch (NoResultException e) {
                super.logger.warn("No Result Exception " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.ofNullable(user);
    }
}
