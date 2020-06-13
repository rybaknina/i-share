package eu.senla.course.service;

import eu.senla.course.api.IMechanic;
import eu.senla.course.entity.Mechanic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MechanicManager implements IMechanic {

    private List<Mechanic> mechanics;

    public MechanicManager() {
        this.mechanics = new ArrayList<>();
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
        mechanics.removeIf(e -> e.equals(mechanic));
    }

    public Mechanic gerMechanicById(int id){
        if (mechanics == null){
            System.out.println("Auto mechanics are not exist");
            return null;
        }
        return mechanics.get(id);
    }
    public Mechanic firstFreeMechanic(){
        for (Mechanic mechanic: mechanics){
            if (mechanic.isMechanicFree()){
                return mechanic;
            }
        }
        System.out.println("Free mechanic is not exist");
        return null;
    }

    public void sortMechanicsBy(Comparator<Mechanic> comparator){
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
