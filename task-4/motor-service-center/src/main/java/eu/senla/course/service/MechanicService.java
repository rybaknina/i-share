package eu.senla.course.service;

import eu.senla.course.api.IMechanicService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.util.CsvException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.PathToFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        // TODO: Need to test
        List<List<String>> lists;
        Path path = this.getPath();

        try {
            lists = CsvReader.readRecords(Files.newBufferedReader(path));
            createMechanics(lists);

        } catch (CsvException e) {
            System.out.println("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createMechanics(List<List<String>> lists) throws ServiceException {
        List<Mechanic> loadedMechanics = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                String[] array = list.stream().toArray(String[]::new);
                int id = Integer.parseInt(array[0]) - 1;
                String name = array[1];

                boolean exist = false;
                Mechanic newMechanic;
                if (mechanics.size() > 0 && Optional.of(mechanics.get(id)).isPresent()) {
                    newMechanic = mechanics.get(id);
                    exist = true;
                } else {
                    newMechanic = new Mechanic(name);
                }
                if (newMechanic != null){
                    newMechanic.setName(name);

                    if (array.length >= 3) {
                        List<String> idOrders = Arrays.asList(array[2].split("\\|"));
                        List<Order> orders = new ArrayList<>();
                        for (String idOrderLine : idOrders) {
                            if (!idOrderLine.isBlank()) {
                                int idOrder = Integer.parseInt(idOrderLine) - 1;
                                Order order = OrderService.getInstance().getOrderById(idOrder);
                                if (order != null) {
                                    orders.add(order);
                                    order.setMechanic(newMechanic);
                                    OrderService.getInstance().updateOrder(idOrder, order);
                                }
                            }
                        }
                        if (orders.size() > 0) {
                            newMechanic.setOrders(orders);
                        }
                    }
                    if (array.length >= 4) {
                        int garageId = Integer.parseInt(array[3]) - 1;
                        Garage garage = GarageService.getInstance().getGarageById(garageId);
                        if (garage != null){
                            newMechanic.setGarage(garage);
                        }
                    }
                    if (exist) {
                        updateMechanic(id, newMechanic);
                    } else {
                        loadedMechanics.add(newMechanic);
                    }
                }
            }
        } catch (Exception e){
            throw new ServiceException("Error with create mechanics from csv");
        }

        loadedMechanics.forEach(System.out::println);
        mechanics.addAll(loadedMechanics);
    }

    @Override
    public void mechanicsToCsv() throws ServiceException {
        // TODO: need more tests
        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add("id");
        headers.add("name");
        headers.add("order_ids");
        headers.add("garage_id");

        try {
            for (Mechanic mechanic: mechanics){
                if (mechanic != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(mechanic.getId()));
                    dataIn.add(mechanic.getName());

                    StringBuilder ordersString = new StringBuilder();
                    List<Order> orders = OrderService.getInstance().getOrders();

                    for (Order order: orders){
                        if (order != null && order.getMechanic().equals(mechanic)){
                            ordersString.append(order.getId());
                            ordersString.append("|");
                        }
                    }

                    dataIn.add(ordersString.toString());
                    if (mechanic.getGarage() != null) {
                        dataIn.add(String.valueOf(mechanic.getGarage().getId()));
                    }
                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(new File(String.valueOf(getPath())), headers, data);

        } catch (CsvException e) {
            System.out.println("Csv write exception" + e.getMessage());
        }
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
