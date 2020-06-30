package eu.senla.course.service;

import eu.senla.course.api.IGarageService;
import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.CsvGarageHeader;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.*;
import eu.senla.course.util.exception.CsvException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class GarageService implements IGarageService {

    private final static IGarageService instance = new GarageService();
    private final static String GARAGE_PATH = "garage";

    private List<Garage> garages;

    private GarageService() {
        this.garages = new ArrayList<>();
    }

    public static IGarageService getInstance(){
        return instance;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public void addGarage(Garage garage) throws ServiceException {
        if (garage == null){
            throw new ServiceException("Garage is not exist");
        }
        garages.add(garage);
        garage.setSpots(createSpots(garage));
    }

    public void updateGarage(Garage garage) throws ServiceException {
        int id = garages.indexOf(garage);
        if (id < 0){
            throw new ServiceException("Garage is not found");
        }
        garages.set(id, garage);
    }

    public void deleteGarage(Garage garage) throws ServiceException {
        if (garages.size() == 0 || garage == null){
            throw new ServiceException("Garage is not found");
        }
        garages.removeIf(e -> e.equals(garage));
        ListUtil.shiftIndex(garages);
        Garage.getCount().getAndDecrement();
    }

    public Garage getGarageById(int id) throws ServiceException {
        if (garages.size() == 0 || garages.get(id) == null){
            throw new ServiceException("Garage is not found");
        }
        return garages.get(id);
    }

    private List<Spot> createSpots(@NotNull Garage garage) throws ServiceException {
        ISpotService spotService = SpotService.getInstance();
        int len = GeneratorUtil.generateNumber();
        for (int i = 0; i < len; i++){
            spotService.addSpot(new Spot(garage));
        }
        return spotService.getSpots();
    }

    public int lengthAllSpots(){
        int len = 0;
        if (garages.size() == 0){
            return len;
        }
        for (Garage garage: garages){
            len += garage.getSpots().size();
        }
        return len;
    }

    private List<Spot> spotsOnDate(LocalDateTime date, List<Order> orders){
        List<Spot> spots = new ArrayList<>();
        if (orders != null && orders.size() != 0) {
            for (Order order : orders) {
                if (order != null && order.getSpot() != null && (order.getCompleteDate() != null &&
                        (order.getCompleteDate().isAfter(date) && order.getStatus() != OrderStatus.CLOSE) ||
                        order.getStatus() == OrderStatus.IN_PROGRESS)) {
                    spots.add(order.getSpot());
                }
            }
        }
        return spots;
    }

    public List<Spot> listAvailableSpots(LocalDateTime date, List<Order> orders) throws ServiceException {
        List<Spot> freeSpots = new ArrayList<>();
        if (garages.size() == 0){
            throw new ServiceException("Garages are not exist");
        }
        for (Garage garage: garages){
            List<Spot> busySpots = spotsOnDate(date, orders);

            for (Spot spot: garage.getSpots()){
                if (spot != null) {
                    if (busySpots.size() == 0 || !busySpots.contains(spot)) {
                        freeSpots.add(spot);
                    }
                }
            }
        }
        return freeSpots;
    }

    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders) throws ServiceException {
        if (orders.size() == 0){
            return 0;
        }
        return (int) listAvailableSpots(futureDate, orders).stream().filter(Objects::nonNull).count();
    }

    @Override
    public void garagesFromCsv() throws ServiceException {

        List<List<String>> lists;
        Path path = Optional.of(Paths.get(PathToFile.getPath(GARAGE_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));

        try {
            lists = CsvReader.readRecords(Files.newBufferedReader(path));
            createGarages(lists);

        } catch (CsvException e) {
            System.out.println("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }

    }

    private Path getPath() throws ServiceException {
        return Optional.of(Paths.get(PathToFile.getPath(GARAGE_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
    }

    private void createGarages(List<List<String>> lists) throws ServiceException {

        List<Garage> loadedGarages = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                int n = 0;
                int id = Integer.parseInt(list.get(n++)) - 1;

                boolean exist = false;
                Garage newGarage;
                if (garages.size() >= (id + 1) && garages.get(id) != null) {
                    newGarage = garages.get(id);
                    exist = true;
                } else {
                    newGarage = new Garage();
                }

                String name = list.get(n++);
                newGarage.setName(name);

                if (list.size() >= (n + 1)) {
                    List<String> idSpots = Arrays.asList(list.get(n++).split("\\|"));
                    List<Spot> spots = new ArrayList<>();
                    for (String idSpotLine : idSpots) {
                        if (!idSpotLine.isBlank()) {
                            int idSpot = Integer.parseInt(idSpotLine) - 1;

                            if (SpotService.getInstance().getSpots().size() >= (idSpot + 1)) {
                                Spot spot = SpotService.getInstance().getSpotById(idSpot);
                                spots.add(spot);
                                spot.setGarage(newGarage);
                                SpotService.getInstance().updateSpot(spot);
                            }
                        }
                    }
                    if (spots.size() > 0) {
                        newGarage.setSpots(spots);
                    }
                }
                if (list.size() >= (n + 1)) {
                    List<String> idMechanics = Arrays.asList(list.get(n).split("\\|"));
                    List<Mechanic> mechanics = new ArrayList<>();
                    for (String idMechanicLine : idMechanics) {
                        if (!idMechanicLine.isBlank()) {
                            int idMechanic = Integer.parseInt(idMechanicLine) - 1;

                            if (MechanicService.getInstance().getMechanics().size() >= (idMechanic + 1)) {
                                Mechanic mechanic = MechanicService.getInstance().getMechanicById(idMechanic);
                                mechanics.add(mechanic);
                                mechanic.setGarage(newGarage);
                                MechanicService.getInstance().updateMechanic(mechanic);
                            }
                        }
                    }
                    if (mechanics.size() > 0) {
                        newGarage.setMechanics(mechanics);
                    }
                }
                if (exist) {
                    updateGarage(newGarage);
                } else {
                    loadedGarages.add(newGarage);
                }
            }
        } catch (Exception e){
            throw new ServiceException("Error with create garage from csv");
        }

        loadedGarages.forEach(System.out::println);
        garages.addAll(loadedGarages);
    }
    public void garagesToCsv() throws ServiceException{
        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add(CsvGarageHeader.ID.getName());
        headers.add(CsvGarageHeader.NAME.getName());
        headers.add(CsvGarageHeader.SPOT_IDS.getName());
        headers.add(CsvGarageHeader.MECHANIC_IDS.getName());

        try {
            for (Garage garage: garages){
                if (garage != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(garage.getId()));
                    dataIn.add(garage.getName());

                    StringBuilder spotsString = new StringBuilder();
                    List<Spot> spots = SpotService.getInstance().getSpots();

                    for (Spot spot: spots){
                        if (spot != null && spot.getGarage().equals(garage)){
                            spotsString.append(spot.getId());
                            spotsString.append("|");
                        }
                    }

                    dataIn.add(spotsString.toString());

                    StringBuilder mechanicsString = new StringBuilder();
                    List<Mechanic> mechanics = MechanicService.getInstance().getMechanics();
                    for (Mechanic mechanic:mechanics){
                        if (mechanic != null && mechanic.getGarage().equals(garage)){
                            mechanicsString.append(mechanic.getId());
                            mechanicsString.append("|");
                        }
                    }
                    dataIn.add(mechanicsString.toString());

                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(new File(String.valueOf(getPath())), headers, data);

        } catch (CsvException e) {
            System.out.println("Csv write exception" + e.getMessage());
        }
    }
}
