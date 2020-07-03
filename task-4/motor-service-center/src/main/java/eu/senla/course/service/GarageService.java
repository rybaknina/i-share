package eu.senla.course.service;

import eu.senla.course.api.IGarageService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.CsvGarageHeader;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.repository.GarageRepository;
import eu.senla.course.repository.MechanicRepository;
import eu.senla.course.repository.SpotRepository;
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
        this.garages = GarageRepository.getInstance().getAll();
    }

    public static IGarageService getInstance(){
        return instance;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        GarageRepository.getInstance().setAll(garages);
    }

    public void addGarage(Garage garage) throws ServiceException {
        try {
            GarageRepository.getInstance().add(garage);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
        garage.setSpots(createSpots(garage));
    }

    public void updateGarage(Garage garage) throws ServiceException {
        try {
            GarageRepository.getInstance().update(garage);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public void deleteGarage(Garage garage) throws ServiceException {
        try {
            SpotRepository.getInstance().getAll().removeIf(spot -> spot.getGarage().equals(garage));
            GarageRepository.getInstance().delete(garage);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public Garage getGarageById(int id) throws ServiceException {
        Garage garage = GarageRepository.getInstance().getById(id);
        if (garage == null){
            throw new ServiceException("Garage is not found");
        }
        return garage;
    }

    private List<Spot> createSpots(@NotNull Garage garage) throws ServiceException {
        SpotRepository spotRepository = SpotRepository.getInstance();
        int len = GeneratorUtil.generateNumber();
        for (int i = 0; i < len; i++){
            try {
                spotRepository.add(new Spot(garage));
            } catch (RepositoryException e) {
                throw new ServiceException("RepositoryException " + e.getMessage());
            }
        }
        return spotRepository.getAll();
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
            throw new ServiceException("Spots are not available");
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
        Path path = this.getPath();

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
                int id = Integer.parseInt(list.get(n++));

                boolean exist = false;
                Garage newGarage = GarageRepository.getInstance().getById(id);
                if (newGarage != null) {
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
                            int idSpot = Integer.parseInt(idSpotLine);
                            Spot spot = SpotRepository.getInstance().getById(idSpot);
                            if (spot != null) {
                                spots.add(spot);
                                spot.setGarage(newGarage);
                                SpotRepository.getInstance().update(spot);
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
                            int idMechanic = Integer.parseInt(idMechanicLine);
                            Mechanic mechanic = MechanicRepository.getInstance().getById(idMechanic);
                            if (mechanic != null) {
                                mechanics.add(mechanic);
                                mechanic.setGarage(newGarage);
                                MechanicRepository.getInstance().update(mechanic);
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
        GarageRepository.getInstance().addAll(loadedGarages);
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
                    List<Spot> spots = SpotRepository.getInstance().getAll();

                    for (Spot spot: spots){
                        if (spot != null && spot.getGarage().equals(garage)){
                            spotsString.append(spot.getId());
                            spotsString.append("|");
                        }
                    }

                    dataIn.add(spotsString.toString());

                    StringBuilder mechanicsString = new StringBuilder();
                    List<Mechanic> mechanics = MechanicRepository.getInstance().getAll();
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
