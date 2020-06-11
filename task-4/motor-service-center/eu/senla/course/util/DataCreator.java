package eu.senla.course.util;

import eu.senla.course.controller.MechanicManager;
import eu.senla.course.view.Workshop;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Service;
import eu.senla.course.entity.Spot;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class DataCreator {
    Workshop workshop = Workshop.getInstance();
    public List<Garage> createGarages(){
        int len = workshop.lengthGarages();
        List<Garage> garages = new ArrayList<>();
        for (int i = 0; i < len; i++){
            Garage garage = new Garage(i+1);
            garage.setSpots(createSpots(garage));
            garages.add(garage);
        }
        workshop.setGarages(garages);
        return garages;
    }

    public List<Spot> createSpots(@NotNull Garage garage){
        int len = GeneratorUtil.generateNumber();
        List<Spot> spots = new ArrayList<>();
        for (int i = 0; i < len; i++){
            spots.add(new Spot(i+1, garage));
        }
        return spots;
    }

    public List<Service> createServices(){

        String[] names = {"Diagnosis", "Check Engine", "Oil Change", "Tyre Change", "Spare Part Change"};
        int[] hours = {1,2,2,1,4};
        BigDecimal[] prices = {new BigDecimal(10), new BigDecimal(35), new BigDecimal(55), new BigDecimal(40), new BigDecimal(70)};
        List<Service> services = new ArrayList<>();

        int len = workshop.lengthServices();
        for (int i = 0; i < len; i++){
            services.add(new Service(i+1, names[i], hours[i], prices[i]));
        }
        workshop.setServices(services);
        return services;
    }

    public List<Mechanic> createMechanics(){
        List<Mechanic> mechanics = new ArrayList<>();
        int len = workshop.lengthMechanics();
        int count = 0;
        char i = 'Z';
        do {
            count++;
            mechanics.add(new Mechanic(count, i + "_Mechanic"));
            i--;
        } while (count < len);
        workshop.setMechanics(mechanics);
        return mechanics;
    }
}
