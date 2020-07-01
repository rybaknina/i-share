package eu.senla.course.service;

import eu.senla.course.api.IMechanicService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.CsvMechanicHeader;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.repository.GarageRepository;
import eu.senla.course.repository.MechanicRepository;
import eu.senla.course.repository.OrderRepository;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.ListUtil;
import eu.senla.course.util.PathToFile;
import eu.senla.course.util.exception.CsvException;

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
        this.mechanics = MechanicRepository.getInstance().getAll();
    }

    public static IMechanicService getInstance(){
        return instance;
    }

    public void addMechanic(Mechanic mechanic) throws ServiceException {
        try {
            MechanicRepository.getInstance().add(mechanic);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        MechanicRepository.getInstance().setAll(mechanics);
    }

    public void deleteMechanic(Mechanic mechanic) throws ServiceException {
        try {
            MechanicRepository.getInstance().delete(mechanic);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
        ListUtil.shiftIndex(mechanics);
        Mechanic.getCount().getAndDecrement();
    }

    public Mechanic getMechanicById(int id) throws ServiceException {
        try {
            return MechanicRepository.getInstance().getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }
    public Mechanic firstFreeMechanic() throws ServiceException {
        if (mechanics.size() == 0){
            throw new ServiceException("Auto mechanics are not exist");
        }
        for (Mechanic mechanic: mechanics){
            if (mechanic.isMechanicFree()){
                return mechanic;
            }
        }
        System.out.println("Free mechanic is not found");
        return null;
    }

    public void sortMechanicsBy(Comparator<Mechanic> comparator) throws ServiceException {
        if (mechanics.size() == 0){
            throw new ServiceException("Auto mechanics are not exist");
        }
        mechanics.sort(comparator);
        for (Mechanic mechanic: mechanics){
            System.out.println(mechanic.getId() + " " + mechanic.getName() + " " + mechanic.isMechanicFree());
        }
    }

    @Override
    public void mechanicsFromCsv() throws ServiceException {

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

                int n = 0;
                int id = Integer.parseInt(list.get(n++)) - 1;
                String name = list.get(n++);

                boolean exist = false;
                Mechanic newMechanic;
                if (mechanics.size() >= (id + 1) && mechanics.get(id) != null) {
                    newMechanic = MechanicRepository.getInstance().getById(id);
                    exist = true;
                } else {
                    newMechanic = new Mechanic(name);
                }

                newMechanic.setName(name);

                if (list.size() >= (n + 1)) {
                    List<String> idOrders = Arrays.asList(list.get(n++).split("\\|"));
                    List<Order> orders = new ArrayList<>();
                    for (String idOrderLine : idOrders) {
                        if (!idOrderLine.isBlank()) {
                            int idOrder = Integer.parseInt(idOrderLine) - 1;

                            if (OrderRepository.getInstance().getAll().size() >= (idOrder + 1)) {
                                Order order = OrderRepository.getInstance().getById(idOrder);
                                orders.add(order);
                                order.setMechanic(newMechanic);
                                OrderRepository.getInstance().update(order);
                            }
                        }
                    }
                    if (orders.size() > 0) {
                        newMechanic.setOrders(orders);
                    }
                }
                if (list.size() >= (n+1)) {
                    int garageId = Integer.parseInt(list.get(n)) - 1;
                    if (GarageRepository.getInstance().getAll().size() >= (garageId + 1)){
                        newMechanic.setGarage(GarageRepository.getInstance().getById(garageId));
                    }
                }
                if (exist) {
                    updateMechanic(newMechanic);
                } else {
                    loadedMechanics.add(newMechanic);
                }

            }
        } catch (Exception e){
            throw new ServiceException("Error with create mechanics from csv");
        }

        loadedMechanics.forEach(System.out::println);
        MechanicRepository.getInstance().addAll(loadedMechanics);
    }

    @Override
    public void mechanicsToCsv() throws ServiceException {
        // TODO: need more tests
        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add(CsvMechanicHeader.ID.getName());
        headers.add(CsvMechanicHeader.NAME.getName());
        headers.add(CsvMechanicHeader.ORDER_IDS.getName());
        headers.add(CsvMechanicHeader.GARAGE_ID.getName());

        try {
            for (Mechanic mechanic: mechanics){
                if (mechanic != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(mechanic.getId()));
                    dataIn.add(mechanic.getName());

                    StringBuilder ordersString = new StringBuilder();
                    for (Order order: OrderRepository.getInstance().getAll()){
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
        return Optional.of(Paths.get(PathToFile.getPath(MECHANIC_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
    }

    public void updateMechanic(Mechanic mechanic) throws ServiceException {
        try {
            MechanicRepository.getInstance().update(mechanic);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
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
