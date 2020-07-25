package eu.senla.course.repository;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SpotRepository implements ISpotRepository {
    private List<Spot> spots = new ArrayList<>();

    @Override
    public void add(Spot spot) throws RepositoryException {
        if (spot == null){
            throw new RepositoryException("Spot is not exist");
        }
        spots.add(spot);
    }

    @Override
    public void delete(Spot spot)  {
        spots.removeIf(e -> e.equals(spot));
    }

    @Override
    public Spot getById(int id) {
        for (Spot spot: spots){
            if (spot.getId() == id){
                return spot;
            }
        }
        return null;
    }

    @Override
    public List<Spot> getAll() {
        return spots;
    }

    public void update(Spot spot) throws RepositoryException{
        int id = spots.indexOf(spot);
        if (id < 0){
            throw new RepositoryException("Spot is not found");
        }
        spots.set(id, spot);
    }
    public void setAll(List<Spot> spots){
        this.spots = spots;
    }
    public void addAll(List<Spot> spots){
        this.spots.addAll(spots);
    }
}
