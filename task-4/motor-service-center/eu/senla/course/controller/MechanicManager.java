package eu.senla.course.controller;

import eu.senla.course.entity.Mechanic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MechanicManager {
    private final static int MAX_NUMBER_MECHANIC = 6;
    private List<Mechanic> mechanics;

    public MechanicManager() {
        this.mechanics = new ArrayList<>();
    }

    public void addMechanic(Mechanic mechanic) {
        if (mechanics.size() == MAX_NUMBER_MECHANIC) {
            System.out.println("Mechanics is full");
        } else {
            mechanics.add(mechanic);
        }
    }

    public int lengthMechanics(){
            return MAX_NUMBER_MECHANIC;
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
        return (mechanics == null)? null: mechanics.get(id);
    }
    public Mechanic firstFreeMechanic(){
        for (Mechanic mechanic: mechanics){
            if (mechanic.isMechanicFree()){
                return mechanic;
            }
        }
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
