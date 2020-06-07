package eu.senla.course.controller;

import eu.senla.course.entity.Mechanic;

import java.util.Arrays;
import java.util.Comparator;

public class MechanicManager {
    private final static int MAX_NUMBER_MECHANIC = 6;
    private Mechanic[] mechanics;

    public MechanicManager() {
        this.mechanics = new Mechanic[MAX_NUMBER_MECHANIC];
    }

    // Добавить мастера
    public void addMechanic(Mechanic mechanic) {
        if (mechanics[MAX_NUMBER_MECHANIC - 1] != null) {
            System.out.println("Mechanics is full");
        } else {
            for (int i = 0; i < MAX_NUMBER_MECHANIC; i++) {
                if (mechanics[i] == null) {
                    mechanics[i] = mechanic;
                    break;
                }
            }
        }
    }

    // удалить мастера
    public void deleteMechanic(int id){
        for (int i=0; i<MAX_NUMBER_MECHANIC; i++){
            if (mechanics[i].getId() == id){
                mechanics[i] = null;
                break;
            }
        }
    }

    public Mechanic[] listMechanics(){
        return mechanics;
    }

    public int lengthMechanics(){
        return mechanics.length;
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
        Arrays.sort(mechanics, comparator);
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
