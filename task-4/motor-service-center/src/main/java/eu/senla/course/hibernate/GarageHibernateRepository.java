package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.enums.sql.SqlGarage;
import eu.senla.course.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class GarageHibernateRepository extends AbstractHibernateRepository<Garage> implements IGarageRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void delete(Garage garage) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Mechanic> mechanics = MechanicController.getInstance().getMechanics();
        for (Mechanic mechanic: mechanics) {
            if (mechanic.getGarage().getId() == garage.getId()) {
                mechanic.setGarage(null);
            }
        }
        session.delete(garage);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setAll(List<Garage> garages) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(SqlGarage.DELETE_ALL.getName()).executeUpdate();
        session.createSQLQuery(SqlGarage.RESET.getName()).executeUpdate();
        for (Garage garage: garages) {
            session.save(garage);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addAll(List<Garage> garages) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Garage garage: garages) {
            session.save(garage);
        }
        session.getTransaction().commit();
        session.close();
    }
}
