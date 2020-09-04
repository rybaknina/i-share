package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.enums.sql.SqlMechanic;
import eu.senla.course.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class MechanicHibernateRepository extends AbstractHibernateRepository<Mechanic> implements IMechanicRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void setAll(List<Mechanic> mechanics) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(SqlMechanic.DELETE_ALL.getName()).executeUpdate();
        session.createSQLQuery(SqlMechanic.RESET.getName()).executeUpdate();
        for (Mechanic mechanic: mechanics) {
            session.save(mechanic);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addAll(List<Mechanic> mechanics) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Mechanic mechanic: mechanics) {
            session.save(mechanic);
        }
        session.getTransaction().commit();
        session.close();
    }
}
