package eu.senla.course.repository;

import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.controller.GarageController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.enums.sql.SqlMechanic;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicRepository implements IMechanicRepository {
    private final static Logger logger = LogManager.getLogger(MechanicRepository.class);
    @Override
    public void add(Mechanic mechanic) throws RepositoryException {
        if (mechanic == null) {
            throw new RepositoryException("Mechanic is not exist");
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlMechanic.INSERT.getName())) {
            ps.setString(1, mechanic.getName());
            ps.setInt(2, mechanic.getGarage().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement ps = connection.prepareStatement(SqlMechanic.DELETE.getName())) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Exception " + e.getMessage());
        }
    }

    @Override
    public Mechanic getById(int id) {
        Mechanic mechanic = null;
        if (id == 0) {
            logger.warn("Wrong Id = " + id);
            return mechanic;
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlMechanic.SELECT_BY_ID.getName())) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString(SqlMechanic.NAME.getName());
                int garageId = rs.getInt(SqlMechanic.GARAGE_ID.getName());
                Garage garage = GarageController.getInstance().getGarageById(garageId);
                mechanic = new Mechanic(id, name, garage);
            }
        } catch (SQLException e) {
            logger.info("SQLException " + e.getMessage());
            mechanic = null;
        }
        return mechanic;
    }

    @Override
    public List<Mechanic> getAll() {
        List<Mechanic> mechanics = new ArrayList<>();
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlMechanic.SELECT_ALL.getName())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(SqlMechanic.ID.getName());
                String name = rs.getString(SqlMechanic.NAME.getName());
                int garageId = rs.getInt(SqlMechanic.GARAGE_ID.getName());
                Garage garage = GarageController.getInstance().getGarageById(garageId);
                mechanics.add(new Mechanic(id, name, garage));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return mechanics;
    }

    public void update(Mechanic mechanic) throws RepositoryException {
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlMechanic.UPDATE.getName())) {
            ps.setString(1, mechanic.getName());
            ps.setInt(2, mechanic.getGarage().getId());
            ps.setInt(3, mechanic.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }
    public void setAll(List<Mechanic> mechanics) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement deleleAll = connection.prepareStatement(SqlMechanic.DELETE_ALL.getName()); PreparedStatement reset = connection.prepareStatement(SqlMechanic.RESET.getName()); PreparedStatement insert = connection.prepareStatement(SqlMechanic.INSERT.getName())) {
            connection.setAutoCommit(false);

            deleleAll.executeUpdate();
            reset.executeUpdate();

            for (Mechanic mechanic: mechanics) {
                insert.setString(1, mechanic.getName());
                insert.setInt(2, mechanic.getGarage().getId());
                insert.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Rollback exception " + e.getMessage());
            }
            logger.error("Exception " + e.getMessage());
        }
    }
    public void addAll(List<Mechanic> mechanics) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement insert = connection.prepareStatement(SqlMechanic.INSERT.getName())) {
            connection.setAutoCommit(false);

            for (Mechanic mechanic: mechanics) {
                insert.setString(1, mechanic.getName());
                insert.setInt(2, mechanic.getGarage().getId());
                insert.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Rollback exception " + e.getMessage());
            }
            logger.error("Exception " + e.getMessage());
        }
    }
}
