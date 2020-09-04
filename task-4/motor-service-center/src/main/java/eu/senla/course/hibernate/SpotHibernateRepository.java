package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.sql.SqlSpot;
import eu.senla.course.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class SpotHibernateRepository extends AbstractHibernateRepository<Spot> implements ISpotRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void setAll(List<Spot> spots) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(SqlSpot.DELETE_ALL.getName()).executeUpdate();
        session.createSQLQuery(SqlSpot.RESET.getName()).executeUpdate();
        for (Spot spot: spots) {
            session.save(spot);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addAll(List<Spot> spots) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Spot spot: spots) {
            session.save(spot);
        }
        session.getTransaction().commit();
        session.close();
    }
}
