package eu.senla.course.hibernate;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.sql.SqlTool;
import eu.senla.course.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class ToolHibernateRepository extends AbstractHibernateRepository<Tool> implements IToolRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void setAll(List<Tool> tools) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(SqlTool.DELETE_ALL.getName()).executeUpdate();
        session.createSQLQuery(SqlTool.RESET.getName()).executeUpdate();
        for (Tool tool: tools) {
            session.save(tool);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addAll(List<Tool> tools) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Tool tool: tools) {
            session.save(tool);
        }
        session.getTransaction().commit();
        session.close();
    }
}
