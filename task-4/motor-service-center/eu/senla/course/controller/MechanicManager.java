package eu.senla.course.controller;

import eu.senla.course.entity.Mechanic;

public class MechanicManager {
    public final static int MAX_NUMBER_MECHANIC = 6;
    Mechanic[] mechanics;

    public MechanicManager() {
        this.mechanics = new Mechanic[MAX_NUMBER_MECHANIC];
    }

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
    public Mechanic firstFreeMechanic(){
        for (Mechanic mechanic: mechanics){
            if (mechanic.isMechanicFree()){
                return mechanic;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Full current staff: \n");
        for (Mechanic mechanic : mechanics) {
            if (mechanic == null) continue;
            stringBuilder.append(mechanic.toString());
        }
        return stringBuilder.toString();
    }
}
