package eu.senla.course.service;

import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.util.CsvException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.PathToFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpotService implements ISpotService {

    private final static ISpotService instance = new SpotService();
    private final static String SPOT_PATH = "spot";

    private List<Spot> spots;

    private SpotService() {
        this.spots = new ArrayList<>();
    }

    public static ISpotService getInstance(){
        return instance;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public void addSpot(Spot spot){
        spots.add(spot);
    }

    public Spot getSpotById(int id){
        if (spots == null || spots.size() == 0){
            System.out.println("Spots are not exist");
            return null;
        }
        return spots.get(id);
    }

    public void deleteSpot(Spot spot){
        if (spots == null || spots.size() == 0){
            System.out.println("Spots are not exist");
        } else {
            spots.removeIf(e -> e.equals(spot));
        }
    }
    public void updateSpot(int id, Spot spot) throws ServiceException {
        // Is it OK?
        Optional.of(spots).orElseThrow(() -> new ServiceException("Spots are not found"));
        Optional.of(spots.set(id, spot)).orElseThrow(() -> new ServiceException("Spot is not found"));;

    }

    private Path getPath() throws ServiceException {
        Path path = Optional.of(Paths.get(new PathToFile().getPath(SPOT_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
        return path;
    }

    @Override
    public void spotsFromCsv() throws ServiceException {
        // TODO: need more tests
        List<List<String>> lists;
        Path path = this.getPath();
        System.out.println(path.toAbsolutePath()); // just for test
        try {
            lists = CsvReader.readRecords(Files.newBufferedReader(path));
            createSpots(lists);

        } catch (CsvException e) {
            System.out.println("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createSpots(List<List<String>> lists) throws ServiceException {
        List<Spot> loadedSpots = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                String[] array = list.stream().toArray(String[]::new);
                int id = Integer.parseInt(array[0]) - 1;
                int garageId = Integer.parseInt(array[1]) - 1;

                Garage garage = GarageService.getInstance().getGarageById(garageId);

                if (garage == null){
                    throw new ServiceException("Garage is not found");
                }

                Spot newSpot;
                if (spots.size() > 0 && Optional.of(spots.get(id)).isPresent()) {
                    newSpot = spots.get(id);
                    newSpot.setGarage(garage);
                    updateSpot(id, newSpot);

                } else {
                    newSpot = new Spot(garage);
                    loadedSpots.add(newSpot);
                }
            }
        } catch (Exception e){
            throw new ServiceException("Error with create spot from csv");
        }

        loadedSpots.forEach(System.out::println);
        spots.addAll(loadedSpots);
    }

    @Override
    public void spotsToCsv() throws ServiceException {
        // TODO: need more tests
        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add("id");
        headers.add("garage_id");

        try {
            for (Spot spot: spots){
                if (spot != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(spot.getId()));
                    dataIn.add(String.valueOf(spot.getGarage().getId()));
                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(new File(String.valueOf(getPath())), headers, data);

        } catch (CsvException e) {
            System.out.println("Csv write exception" + e.getMessage());
        }
    }
}
