package eu.senla.course.util;

import eu.senla.course.entity.Tool;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class DataCreator {
    private final static int MAX_GARAGES = 4;
    private final static int MAX_MECHANICS = 5;

    private ServiceProvider provider = ServiceProvider.getInstance();
    public List<Garage> createGarages(){
        int len = MAX_GARAGES;
        List<Garage> garages = new ArrayList<>();
        for (int i = 0; i < len; i++){
            Garage garage = new Garage(i+1);
            garages.add(garage);
            provider.getGarageService().addGarage(garage);
        }
        provider.getGarageService().setGarages(garages);
        return garages;
    }

    public List<Tool> createServices(){

        String[] names = {"Diagnosis", "Check Engine", "Oil Change", "Tyre Change", "Spare Part Change"};
        int[] hours = {1,2,2,1,4};
        BigDecimal[] prices = {new BigDecimal(10), new BigDecimal(35), new BigDecimal(55), new BigDecimal(40), new BigDecimal(70)};
        List<Tool> tools = new ArrayList<>();

        int len = names.length;
        for (int i = 0; i < len; i++){
            tools.add(new Tool(i+1, names[i], hours[i], prices[i]));
        }
        provider.getToolService().setTools(tools);
        return tools;
    }

    public List<Mechanic> createMechanics(){
        List<Mechanic> mechanics = new ArrayList<>();
        int len = MAX_MECHANICS;
        int count = 0;
        char i = 'Z';
        do {
            count++;
            mechanics.add(new Mechanic(count, i + "_Mechanic"));
            i--;
        } while (count < len);
        provider.getMechanicService().setMechanics(mechanics);
        return mechanics;
    }
}
