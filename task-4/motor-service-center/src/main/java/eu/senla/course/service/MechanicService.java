package eu.senla.course.service;

import eu.senla.course.api.IMechanicService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.util.PathToFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MechanicService implements IMechanicService {
    private final static IMechanicService instance = new MechanicService();
    private final static String MECHANIC_PATH = "mechanic";
    private List<Mechanic> mechanics;

    private MechanicService() {
        this.mechanics = new ArrayList<>();
    }

    public static IMechanicService getInstance(){
        return instance;
    }

    public void addMechanic(Mechanic mechanic) {
        mechanics.add(mechanic);
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public void deleteMechanic(Mechanic mechanic){
        if (mechanics == null || mechanics.size() == 0){
            System.out.println("Auto mechanics are not exist");
        } else {
            mechanics.removeIf(e -> e.equals(mechanic));
        }
    }

    public Mechanic getMechanicById(int id){
        if (mechanics == null || mechanics.size() == 0){
            System.out.println("Auto mechanics are not exist");
            return null;
        }
        return mechanics.get(id);
    }
    public Mechanic firstFreeMechanic(){
        if (mechanics == null || mechanics.size() == 0){
            System.out.println("Auto mechanics are not exist");
            return null;
        }
        for (Mechanic mechanic: mechanics){
            if (mechanic.isMechanicFree()){
                return mechanic;
            }
        }
        System.out.println("Free mechanic is not exist");
        return null;
    }

    public void sortMechanicsBy(Comparator<Mechanic> comparator){
        if (mechanics == null || mechanics.size() == 0){
            System.out.println("Auto mechanics are not exist");
            return;
        }
        mechanics.sort(comparator);
        for (Mechanic mechanic: mechanics){
            System.out.println(mechanic.getId() + " " + mechanic.getName() + " " + mechanic.isMechanicFree());
        }
    }

    @Override
    public void mechanicsFromCsv() throws ServiceException {
        // TODO: Need implementation
    }

    @Override
    public void mechanicsToCsv() throws ServiceException {
        // TODO: Need implementation
    }

    private Path getPath() throws ServiceException {
        Path path = Optional.of(Paths.get(new PathToFile().getPath(MECHANIC_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
        return path;
    }

    public void updateMechanic(int id, Mechanic mechanic) throws ServiceException {
        // Is it OK?
        Optional.of(mechanics).orElseThrow(() -> new ServiceException("Mechanics are not found"));
        Optional.of(mechanics.set(id, mechanic)).orElseThrow(() -> new ServiceException("Mechanic is not found"));;

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\nFull current staff: \n");
        for (Mechanic mechanic : mechanics) {
            if (mechanic != null) {
                stringBuilder.append(mechanic.getId()).append(" ").append(mechanic.getName()).append(" ").append(mechanic.getGarage().getId()).append("; ");
            }
        }
        return stringBuilder.toString();
    }

}
