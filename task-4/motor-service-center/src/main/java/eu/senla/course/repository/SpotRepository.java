package eu.senla.course.repository;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.controller.GarageController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.sql.SqlSpot;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpotRepository implements ISpotRepository {

    @Override
    public void add(Spot spot) throws RepositoryException {
        if (spot == null){
            throw new RepositoryException("Spot is not exist");
        }
        Connection connection = ConnectionUtil.getInstance().connect();
        try ( PreparedStatement ps = connection.prepareStatement(SqlSpot.INSERT.getName())){
            ps.setInt(1, spot.getGarage().getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }

    @Override
    public void delete(Spot spot)  {
        Connection connection = ConnectionUtil.getInstance().connect();

        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.DELETE.getName())){

            ps.setInt(1, spot.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Exception " + e.getMessage());
        }
    }

    @Override
    public Spot getById(int id) {
        Spot spot = null;
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.SELECT_BY_ID.getName())){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int garageId = rs.getInt(SqlSpot.GARAGE_ID.getName());
                Garage garage = GarageController.getInstance().getGarageById(garageId);
                spot = new Spot(id, garage);
            }

        } catch (SQLException e) {
            spot = null;
        }
        return spot;
    }

    @Override
    public List<Spot> getAll() {
        List<Spot> spots = new ArrayList<>();
        Connection connection = ConnectionUtil.getInstance().connect();
        try (PreparedStatement ps = connection.prepareStatement(SqlSpot.SELECT_ALL.getName())){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(SqlSpot.ID.getName());
                int garageId = rs.getInt(SqlSpot.GARAGE_ID.getName());
                Garage garage = GarageController.getInstance().getGarageById(garageId);
                spots.add(new Spot(id, garage));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return spots;
    }

    public void update(Spot spot) throws RepositoryException{
        Connection connection = ConnectionUtil.getInstance().connect();
        try ( PreparedStatement ps = connection.prepareStatement(SqlSpot.UPDATE.getName())) {
            ps.setInt(1, spot.getGarage().getId());
            ps.setInt(2, spot.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception " + e.getMessage());
        }
    }
    public void setAll(List<Spot> spots){
        //this.spots = spots;
    }
    public void addAll(List<Spot> spots){
        //this.spots.addAll(spots);
    }
}
