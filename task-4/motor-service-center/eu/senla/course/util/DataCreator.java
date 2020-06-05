package eu.senla.course.util;

import eu.senla.course.controller.MechanicManager;
import eu.senla.course.controller.Workshop;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Service;
import eu.senla.course.entity.Spot;
import org.jetbrains.annotations.NotNull;


public class DataCreator {
//    private Workshop workshop;
//    private MechanicManager mechanicManager;

//    public DataCreator(Workshop workshop, MechanicManager mechanicManager) {
//        this.workshop = new Workshop();
//        this.mechanicManager = new MechanicManager();
//    }

    public Garage[] createGarages(Workshop workshop){
        int len = workshop.lengthGarages();
        int count = 0;
        do {
            Garage garage = new Garage(count+1, GeneratorUtil.generateNumber());
            workshop.addGarage(garage);
            // при создании гаража создаются места. Количество мест = capacity Гаража - рандом от 1 до 6
            garage.setSpots(createSpots(garage));
            count++;
        } while (count < len);
        return workshop.listGarages();
    }

    public Spot[] createSpots(@NotNull Garage garage){
        int len = garage.getSpots().length;
        int count = 0;
        do {
            Spot spot = new Spot(count+1, garage);
            garage.addSpot(spot);
            count++;
        } while (count < len);
        return garage.getSpots();
    }

    public Service[] createServices(Workshop workshop){
        int len = workshop.lengthServices();
        String [] names = {"Diagnosis", "Check Engine", "Oil Change", "Tyre Change", "Spare Part Change"};
        int[] hours = {1,2,2,1,4};
        double[] prices = {10, 35, 55, 40, 70};
        int count = 0;
        do {
            workshop.addService(new Service(count+1, names[count], hours[count], prices[count]));
            count ++;
        } while (count < len);
        return workshop.listServices();
    }

    public Mechanic[] createMechanics(MechanicManager mechanicManager){
        int len = mechanicManager.lengthMechanics();
        int count = 0;
        char i = 'Z';
        do {
            count++;
            mechanicManager.addMechanic(new Mechanic(count, i + "_Mechanic"));
            i--;
        } while (count < len);

        return mechanicManager.listMechanics();
    }
}
