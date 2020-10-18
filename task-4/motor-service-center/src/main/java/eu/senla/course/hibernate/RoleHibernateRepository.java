package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IRoleRepository;
import eu.senla.course.entity.Permission;
import eu.senla.course.entity.Role;
import eu.senla.course.entity.Role_;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Component("roleHibernateRepository")
public class RoleHibernateRepository extends AbstractHibernateRepository<Role> implements IRoleRepository {
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    @Override
    public Role findByName(String name) {
        if (!name.isEmpty()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
            Root<Role> rootEntry = criteria.from(Role.class);
            ListJoin<Role, Permission> roleJoin = rootEntry.join(Role_.permissions, JoinType.INNER);
            TypedQuery<Role> query = entityManager.createQuery(criteria.select(rootEntry).where(builder.equal(rootEntry.get("name"), name)));
            Role role;
            try {
                role = query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
            return role;
        }
        return null;
    }
}
