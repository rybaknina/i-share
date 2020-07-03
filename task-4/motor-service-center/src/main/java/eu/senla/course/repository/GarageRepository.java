package eu.senla.course.repository;

import eu.senla.course.api.IRepository;
import eu.senla.course.entity.Garage;
import eu.senla.course.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class GarageRepository implements IRepository<Garage> {
    private List<Garage> garages;
    private static final GarageRepository instance = new GarageRepository();
    private GarageRepository(){
        garages = new ArrayList<>();
    }
    public static GarageRepository getInstance(){
        return instance;
    }

    @Override
    public void add(Garage garage) throws RepositoryException {
        if (garage == null){
            throw new RepositoryException("Garage is not exist");
        }
        garages.add(garage);
    }

    @Override
    public void delete(Garage garage) throws RepositoryException {
        if (garages.size() == 0 || garage == null){
            throw new RepositoryException("Garage is not found");
        }
        garages.removeIf(e -> e.equals(garage));
    }

    @Override
    public Garage getById(int id) {
        for (Garage garage: garages){
            if (garage.getId() == id){
                return garage;
            }
        }
        return null;
    }

    @Override
    public List<Garage> getAll() {
        return garages;
    }

    public void update(Garage garage) throws RepositoryException {
        int id = garages.indexOf(garage);
        if (id < 0){
            throw new RepositoryException("Garage is not found");
        }
        garages.set(id, garage);
    }

    public void setAll(List<Garage> garages) {
        this.garages = garages;
    }

    public void addAll(List<Garage> garages) {
        this.garages.addAll(garages);
    }
}
