package eu.senla.course.hibernate;

import eu.senla.course.api.repository.IPermissionRepository;
import eu.senla.course.entity.Permission;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component("permissionHibernateRepository")
public class PermissionHibernateRepository extends AbstractHibernateRepository<Permission> implements IPermissionRepository {
    @PersistenceContext(unitName = "jpa.hibernate")
    private EntityManager entityManager;

    @Override
    public Permission findByName(String name) {
        if (!name.isEmpty()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Permission> criteria = builder.createQuery(Permission.class);
            Root<Permission> rootEntry = criteria.from(Permission.class);
            TypedQuery<Permission> query = entityManager.createQuery(criteria.select(rootEntry).where(builder.equal(rootEntry.get("name"), name)));
            Permission permission;
            try {
                permission = query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
            return permission;
        }
        return null;
    }
}
