package eu.senla.course.service;

import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.api.service.ISpotService;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.CsvSpotHeader;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.exception.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SpotService implements ISpotService {
    private final static Logger logger = LogManager.getLogger(SpotService.class);

    @Value("${spot}")
    private String spotPath;
    @Value("${modify.spot}")
    private boolean isModifySpot;

    private ISpotRepository spotRepository;
    @Autowired
    public void setSpotRepository(ISpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public List<Spot> getSpots() {
        return spotRepository.getAll();
    }

    public void setSpots(List<Spot> spots) {
        spotRepository.setAll(spots);
    }

    public void addSpot(Spot spot) throws ServiceException {
        try {
            spotRepository.add(spot);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Override
    public boolean isModifySpot() {
        return isModifySpot;
    }

    public Spot getSpotById(int id) {
        return spotRepository.getById(id);
    }

    public void deleteSpot(int id) {
        spotRepository.delete(id);
    }

    public void updateSpot(Spot spot) throws ServiceException {
        try {
            spotRepository.update(spot);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public List<Spot> spotsInGarage(Garage garage) {
        List<Spot> spotList = new ArrayList<>();
        if (garage != null) {
            List<Spot> spots = SpotController.getInstance().getSpots();
            for (Spot spot: spots) {
                if (spot.getGarage().getId() == garage.getId()) {
                    spotList.add(spot);
                }
            }
        }
        return spotList;
    }

    @Override
    public void spotsFromCsv() throws ServiceException {

        List<List<String>> lists;
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(spotPath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createSpots(lists);
            }
        } catch (CsvException e) {
            logger.info("Csv Reader exception " + e.getMessage());
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

                Garage garage = GarageController.getInstance().getGarageById(garageId);
                if (garage == null) {
                    throw new ServiceException("Garage is not found");
                }

                Spot newSpot = spotRepository.getById(id);
                if (newSpot != null) {
                    newSpot.setGarage(garage);
                    updateSpot(newSpot);
                } else {
                    newSpot = new Spot(garage);
                    loadedSpots.add(newSpot);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error with create spot from csv");
        }

        loadedSpots.forEach(System.out::println);
        spotRepository.addAll(loadedSpots);
    }

    @Override
    public void spotsToCsv() {

        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(spotPath);
            for (Spot spot: spotRepository.getAll()) {
                if (spot != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(spot.getId()));
                    dataIn.add(String.valueOf(spot.getGarage().getId()));
                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(file, headerCsv(), data);
        } catch (CsvException e) {
            logger.warn("Csv write exception " + e.getMessage());
        }
    }

    private List<String> headerCsv() {
        List<String> header = new ArrayList<>();
        header.add(CsvSpotHeader.ID.getName());
        header.add(CsvSpotHeader.GARAGE_ID.getName());
        return header;
    }
}
