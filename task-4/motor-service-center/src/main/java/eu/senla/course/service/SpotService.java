package eu.senla.course.service;

import eu.senla.course.annotation.di.Service;
import eu.senla.course.annotation.property.ConfigProperty;
import eu.senla.course.api.service.ISpotService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.ConfigType;
import eu.senla.course.enums.CsvSpotHeader;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.repository.GarageRepository;
import eu.senla.course.repository.SpotRepository;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.exception.CsvException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotService implements ISpotService {

    @ConfigProperty(key = "spot")
    private static String spotPath;
    @ConfigProperty(key = "modify.spot", type = ConfigType.BOOLEAN)
    private static boolean isModifySpot;

    private List<Spot> spots;

    public SpotService() {
        this.spots = SpotRepository.getInstance().getAll();
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        SpotRepository.getInstance().setAll(spots);
    }

    public void addSpot(Spot spot) throws ServiceException {
        try {
            SpotRepository.getInstance().add(spot);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Override
    public boolean isModifySpot() {
        return isModifySpot;
    }

    public Spot getSpotById(int id) throws ServiceException {
        Spot spot = SpotRepository.getInstance().getById(id);
        if (spot == null){
            throw new ServiceException("Spot is not found");
        }
        return spot;
    }

    public void deleteSpot(Spot spot) throws ServiceException {
        try {
            SpotRepository.getInstance().delete(spot);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public void updateSpot(Spot spot) throws ServiceException {
        try {
            SpotRepository.getInstance().update(spot);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }


    @Override
    public void spotsFromCsv() throws ServiceException {

        List<List<String>> lists;
        Path path = Paths.get(spotPath);

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

                int n = 0;
                int id = Integer.parseInt(list.get(n++));
                int garageId = Integer.parseInt(list.get(n));

                Garage garage = GarageRepository.getInstance().getById(garageId);
                if (garage == null){
                    throw new ServiceException("Garage is not found");
                }

                Spot newSpot = SpotRepository.getInstance().getById(id);
                if (newSpot != null) {
                    newSpot.setGarage(garage);
                    updateSpot(newSpot);

                } else {
                    newSpot = new Spot(garage);
                    loadedSpots.add(newSpot);
                }
            }
        } catch (Exception e){
            throw new ServiceException("Error with create spot from csv");
        }

        loadedSpots.forEach(System.out::println);
        SpotRepository.getInstance().addAll(loadedSpots);
    }

    @Override
    public void spotsToCsv() {

        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add(CsvSpotHeader.ID.getName());
        headers.add(CsvSpotHeader.GARAGE_ID.getName());

        try {
            for (Spot spot: spots){
                if (spot != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(spot.getId()));
                    dataIn.add(String.valueOf(spot.getGarage().getId()));
                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(new File(String.valueOf(spotPath)), headers, data);

        } catch (CsvException e) {
            System.out.println("Csv write exception" + e.getMessage());
        }
    }
}
