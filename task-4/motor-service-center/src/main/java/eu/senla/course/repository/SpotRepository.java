package eu.senla.course.repository;

import eu.senla.course.api.IRepository;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class SpotRepository implements IRepository<Spot> {
    private List<Spot> spots;
    private static final SpotRepository instance = new SpotRepository();

    private SpotRepository(){
        spots = new ArrayList<>();
    }

    public static SpotRepository getInstance(){
        return instance;
    }

    @Override
    public void add(Spot spot) throws RepositoryException {
        if (spot == null){
            throw new RepositoryException("Spot is not exist");
        }
        spots.add(spot);
    }

    @Override
    public void delete(Spot spot) throws RepositoryException {
        if (spots.size() == 0 || spot == null){
            throw new RepositoryException("Spot is not found");
        }
        spots.removeIf(e -> e.equals(spot));
    }

    @Override
    public Spot getById(int id) throws RepositoryException {
        if (spots.size() <= id || spots.get(id) == null){
            throw new RepositoryException("Spot is not found");
        }
        return spots.get(id);
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
