package eu.senla.course.service;

import eu.senla.course.api.IGarageService;
import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.Spot;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvException;
import eu.senla.course.util.GeneratorUtil;
import eu.senla.course.util.PathToFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void deleteGarage(Garage garage){
        if (garage == null){
            System.out.println("Garage not found");
            return;
        }
        garages.removeIf(e -> e.equals(garage));
    }

    public Garage getGarageById(int id){
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
            spotService.addSpot(new Spot(i+1, garage));
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
        Path path;
        try {
            path = Paths.get(new PathToFile().getPath(GARAGE_PATH));
            if (path == null){
                throw new ServiceException("Settings for garage is not found");
            }
        } catch (NullPointerException e) {
            throw new ServiceException("Error get property");
        }
        System.out.println(path.toAbsolutePath()); // just for test
        try {
            lists = new CsvReader().readRecords(Files.newBufferedReader(path));
            createGarages(lists);

        } catch (CsvException e) {
            throw new ServiceException("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }

    }

    private void createGarages(List<List<String>> lists) throws ServiceException {
        List<Garage> loadedGarages = new ArrayList<>();
        for (List<String> list : lists) {
            for (int j = 0; j < list.size(); j++) {
                try {
                    Garage garage = new Garage();
                    garage.setName(String.valueOf(list.get(j)));
                    loadedGarages.add(garage);
                } catch (Exception e) {
                    throw new ServiceException("Error work with data from csv");
                }
            }
        }
        loadedGarages.forEach(System.out::println);

        garages.addAll(loadedGarages);
    }
}
