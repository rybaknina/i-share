package eu.senla.course.service;

import eu.senla.course.api.IMechanicService;
import eu.senla.course.entity.Mechanic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MechanicService implements IMechanicService {
    private final static IMechanicService instance = new MechanicService();
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

    public Mechanic gerMechanicById(int id){
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
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\nFull current staff: \n");
        for (Mechanic mechanic : mechanics) {
            if (mechanic != null) {
                stringBuilder.append(mechanic.getId()).append(" ").append(mechanic.getName()).append("; ");
            }
        }
        return stringBuilder.toString();
    }
}