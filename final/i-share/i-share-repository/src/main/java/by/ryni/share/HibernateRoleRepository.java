package by.ryni.share;

import by.ryni.share.entity.Role;
import by.ryni.share.api.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository("roleRepository")
public class HibernateRoleRepository extends AbstractHibernateRepository<Role> implements RoleRepository {
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    public HibernateRoleRepository() {
        super(Role.class);
    }

    @Override
    public Optional<Role> findByName(String name) {
        if (!name.isEmpty()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
            Root<Role> rootEntry = criteria.from(Role.class);
            TypedQuery<Role> query = entityManager.createQuery(criteria.select(rootEntry).where(builder.equal(rootEntry.get("name"), name)));
            Role role;
            try {
                role = query.getSingleResult();
            } catch (NoResultException e) {
                super.logger.warn("No Result Exception " + e.getMessage());
                return Optional.empty();
            }
            return Optional.ofNullable(role);
        }
        return Optional.empty();
    }
}
