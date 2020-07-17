package eu.senla.course.repository;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.entity.Garage;
import eu.senla.course.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GarageRepository implements IGarageRepository {
    private List<Garage> garages = new ArrayList<>();

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

    @Override
    public void update(Garage garage) throws RepositoryException {
        int id = garages.indexOf(garage);
        if (id < 0){
            throw new RepositoryException("Garage is not found");
        }
        garages.set(id, garage);
    }

    @Override
    public void setAll(List<Garage> garages) {
        this.garages = garages;
    }

    @Override
    public void addAll(List<Garage> garages) {
        this.garages.addAll(garages);
    }
}
