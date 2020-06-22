package eu.senla.course.service;

import eu.senla.course.api.IGarageService;
import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.*;
import eu.senla.course.util.*;
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

    public void addGarage(Garage garage){
        garages.add(garage);
        if (garage != null){
            garage.setSpots(createSpots(garage));
        }
    }

    public void updateGarage(int id, Garage garage) throws ServiceException {
        // Is it OK?
        Optional.of(garages).orElseThrow(() -> new ServiceException("Garages are not found"));
        Optional.of(garages.set(id, garage)).orElseThrow(() -> new ServiceException("Garage is not found"));;

    }

    public void deleteGarage(Garage garage) {
        if (garages == null || garages.size() == 0 || garage == null){
            System.out.println("Garage not found");
            return;
        }
        garages.removeIf(e -> e.equals(garage));
    }

    public Garage getGarageById(int id) {
        if (garages == null || garages.size() == 0 || garages.get(id) == null){
            System.out.println("Garage is not found");
            return null;
        }
        return garages.get(id);
    }

    public List<Spot> createSpots(@NotNull Garage garage){
        ISpotService spotService = SpotService.getInstance();
        int len = GeneratorUtil.generateNumber();
        for (int i = 0; i < len; i++){
            spotService.addSpot(new Spot(garage));
        }
        return spotService.getSpots();
    }

    public int lengthAllSpots(){
        int len = 0;
        if (garages == null || garages.size() == 0){
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

    public List<Spot> listAvailableSpots(LocalDateTime date, List<Order> orders){
        List<Spot> freeSpots = new ArrayList<>();
        if (garages == null || garages.size() == 0){
            System.out.println("Garages are not exist");
            return null;
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

    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders){
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");
            return 0;
        }
        return (int) listAvailableSpots(futureDate, orders).stream().filter(Objects::nonNull).count();
    }

    @Override
    public void garagesFromCsv() throws ServiceException {

        List<List<String>> lists;
        Path path = this.getPath();
        System.out.println(path.toAbsolutePath()); // just for test
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
        Path path = Optional.of(Paths.get(new PathToFile().getPath(GARAGE_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
        return path;
    }

    private void createGarages(List<List<String>> lists) throws ServiceException {

        // TODO: Check and fix some bugs

        List<Garage> loadedGarages = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                String[] array = list.stream().toArray(String[]::new);
                int id = Integer.parseInt(array[0]) - 1;

                boolean exist = false;
                Garage newGarage;
                if (garages.size() > 0 && Optional.of(garages.get(id)).isPresent()) {  // ??
                    newGarage = garages.get(id);
                    exist = true;
                } else {
                    newGarage = new Garage();
                }
                if (newGarage != null){

                    String name = array[1];
                    newGarage.setName(name);

                    if (array.length >= 3) {
                        List<String> idSpots = Arrays.asList(array[2].split("\\|"));
                        List<Spot> spots = new ArrayList<>();
                        for (String idSpotLine : idSpots) {
                            if (!idSpotLine.isBlank()) {
                                int idSpot = Integer.parseInt(idSpotLine) - 1;
                                Spot spot = SpotService.getInstance().getSpotById(idSpot);
                                if (spot != null) {
                                    spot.setGarage(newGarage);
                                    SpotService.getInstance().updateSpot(idSpot, spot);
                                }
                            }
                        }
                        if (spots.size() > 0) {
                            newGarage.setSpots(spots);
                        }
                    }
                    if (array.length >= 4) {
                        List<String> idMechanics = Arrays.asList(array[3].split("\\|"));
                        List<Mechanic> mechanics = new ArrayList<>();
                        for (String idMechanicLine : idMechanics) {
                            if (!idMechanicLine.isBlank()) {
                                int idMechanic = Integer.parseInt(idMechanicLine) - 1;
                                Mechanic mechanic = MechanicService.getInstance().getMechanicById(idMechanic);
                                if (mechanic != null) {
                                    mechanic.setGarage(newGarage);
                                    MechanicService.getInstance().updateMechanic(idMechanic, mechanic);
                                }
                            }
                        }
                        if (mechanics.size() > 0) {
                            newGarage.setMechanics(mechanics);
                        }
                    }
                    if (exist) {
                        updateGarage(id, newGarage);
                    } else {
                        loadedGarages.add(newGarage);
                    }
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
        headers.add("id");
        headers.add("name");
        headers.add("spot_ids");
        headers.add("mechanic_ids");

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
