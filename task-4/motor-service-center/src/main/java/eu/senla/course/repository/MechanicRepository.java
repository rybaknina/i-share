package eu.senla.course.repository;

import eu.senla.course.api.repository.IRepository;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class MechanicRepository implements IRepository<Mechanic> {
    private List<Mechanic> mechanics;
    private static final MechanicRepository instance = new MechanicRepository();

    private MechanicRepository(){
        mechanics = new ArrayList<>();
    }

    public static MechanicRepository getInstance(){
        return instance;
    }

    @Override
    public void add(Mechanic mechanic) throws RepositoryException {
        if (mechanic == null){
            throw new RepositoryException("Mechanic is not exist");
        }
        mechanics.add(mechanic);
    }

    @Override
    public void delete(Mechanic mechanic) throws RepositoryException {
        if (mechanics.size() == 0 || mechanic == null){
            throw new RepositoryException("Auto mechanic is not found");
        }
        mechanics.removeIf(e -> e.equals(mechanic));
    }

    @Override
    public Mechanic getById(int id) {
        for (Mechanic mechanic: mechanics){
            if (mechanic.getId() == id){
                return mechanic;
            }
        }
        return null;
    }

    @Override
    public List<Mechanic> getAll() {
        return mechanics;
    }

    public void update(Mechanic mechanic) throws RepositoryException{
        int id = mechanics.indexOf(mechanic);
        if (id < 0){
            throw new RepositoryException("Mechanic is not found");
        }
        mechanics.set(id, mechanic);
    }
    public void setAll(List<Mechanic> mechanics){
        this.mechanics = mechanics;
    }
    public void addAll(List<Mechanic> mechanics){
        this.mechanics.addAll(mechanics);
    }
}
