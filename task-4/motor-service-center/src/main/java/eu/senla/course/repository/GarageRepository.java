package eu.senla.course.repository;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.entity.Garage;
import eu.senla.course.enums.sql.SqlGarage;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GarageRepository implements IGarageRepository {


    @Override
    public void add(Garage garage) throws RepositoryException {
        if (garage == null){
            throw new RepositoryException("Garage is not exist");
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try ( PreparedStatement ps = connection.prepareStatement(SqlGarage.INSERT.getName())){
            ps.setString(1, garage.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }

    @Override
    public void delete(Garage garage) {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement psChild = connection.prepareStatement(SqlGarage.DELETE_SPOTS_IN_GARAGE.getName()); PreparedStatement ps = connection.prepareStatement(SqlGarage.DELETE.getName())){
            connection.setAutoCommit(false);

            psChild.setInt(1, garage.getId());
            psChild.executeUpdate();

            ps.setInt(1, garage.getId());
            ps.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.err.println("Rollback exception " + e.getMessage());
            }
            System.err.println("Exception " + e.getMessage());
        }
    }

    @Override
    public Garage getById(int id) {
        Garage garage = null;
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlGarage.SELECT_BY_ID.getName())){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString(SqlGarage.NAME.getName());
                garage = new Garage(id, name);
            }

        } catch (SQLException e) {
            garage = null;
        }
        return garage;
    }

    @Override
    public List<Garage> getAll() {
        List<Garage> garages = new ArrayList<>();
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlGarage.SELECT_ALL.getName())){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(SqlGarage.ID.getName());
                String name = rs.getString(SqlGarage.NAME.getName());
                garages.add(new Garage(id, name));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return garages;
    }

    @Override
    public void update(Garage garage) throws RepositoryException {
        Connection connection = ConnectionUtil.getInstance().connect();
        try ( PreparedStatement ps = connection.prepareStatement(SqlGarage.UPDATE.getName())) {
            ps.setString(1, garage.getName());
            ps.setInt(2, garage.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }

    @Override
    public void setAll(List<Garage> garages) {
     //  this.garages = garages;
    }

    @Override
    public void addAll(List<Garage> garages) {
     //   this.garages.addAll(garages);
    }
}
