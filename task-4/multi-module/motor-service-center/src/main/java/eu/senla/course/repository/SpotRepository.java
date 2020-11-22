package eu.senla.course.repository;

import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.sql.SqlSpot;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("spotRepository")
public class SpotRepository implements ISpotRepository {
    private final static Logger logger = LogManager.getLogger(SpotRepository.class);
    private IGarageRepository garageRepository;

    @Autowired
    @Qualifier("garageRepository")
    public void setGarageRepository(IGarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Override
    public void add(Spot spot) throws RepositoryException {
        if (spot == null) {
            throw new RepositoryException("Spot is not exist");
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.INSERT.getName())) {
            ps.setInt(1, spot.getGarage().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }

    @Override
    public void delete(int id)  {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.DELETE.getName())) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQLException " + e.getMessage());
        }
    }

    @Override
    public Spot getById(int id) {
        Spot spot = null;
        if (id == 0) {
            logger.warn("Wrong Id = " + id);
            return spot;
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.SELECT_BY_ID.getName())) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int garageId = rs.getInt(SqlSpot.GARAGE_ID.getName());
                Garage garage = garageRepository.getById(garageId);
                spot = new Spot(id, garage);
            }
        } catch (SQLException e) {
            logger.info("SQLException " + e.getMessage());
            spot = null;
        }
        return spot;
    }

    @Override
    public List<Spot> getAll() {
        List<Spot> spots = new ArrayList<>();
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.SELECT_ALL.getName())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(SqlSpot.ID.getName());
                int garageId = rs.getInt(SqlSpot.GARAGE_ID.getName());
                Garage garage = garageRepository.getById(garageId);
                spots.add(new Spot(id, garage));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return spots;
    }

    public void update(Spot spot) throws RepositoryException {
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.UPDATE.getName())) {
            ps.setInt(1, spot.getGarage().getId());
            ps.setInt(2, spot.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }
    public void setAll(List<Spot> spots) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement deleleAll = connection.prepareStatement(SqlSpot.DELETE_ALL.getName()); PreparedStatement reset = connection.prepareStatement(SqlSpot.RESET.getName()); PreparedStatement insert = connection.prepareStatement(SqlSpot.INSERT.getName())) {
            connection.setAutoCommit(false);

            deleleAll.executeUpdate();
            reset.executeUpdate();

            for (Spot spot: spots) {
                insert.setInt(1, spot.getGarage().getId());
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
    public void addAll(List<Spot> spots) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement insert = connection.prepareStatement(SqlSpot.INSERT.getName())) {
            connection.setAutoCommit(false);

            for (Spot spot: spots) {
                insert.setInt(1, spot.getGarage().getId());
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