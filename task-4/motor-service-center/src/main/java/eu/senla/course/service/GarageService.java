package eu.senla.course.service;

import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.api.service.IGarageService;
import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.api.service.ISpotService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.CsvGarageHeader;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.GeneratorUtil;
import eu.senla.course.util.exception.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class GarageService implements IGarageService {
    private final static Logger logger = LogManager.getLogger(GarageService.class);
    @Value("${garage}")
    private String garagePath;

    private IGarageRepository garageRepository;
    private ISpotService spotService;
    private IMechanicService mechanicService;

    @Autowired
    public void setGarageRepository(IGarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Autowired
    public void setSpotService(ISpotService spotService) {
        this.spotService = spotService;
    }

    @Autowired
    public void setMechanicService(IMechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    public List<Garage> getGarages() {
        return garageRepository.getAll();
    }

    public void setGarages(List<Garage> garages) {
        garageRepository.setAll(garages);
    }

    public void addGarage(Garage garage) throws ServiceException {
        try {
            garageRepository.add(garage);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
        garage.setSpots(createSpots(garage));
    }

    public void updateGarage(Garage garage) throws ServiceException {
        try {
            garageRepository.update(garage);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public void deleteGarage(int id) {
        garageRepository.delete(id);
    }

    public Garage getGarageById(int id) {
        return garageRepository.getById(id);
    }

    private List<Spot> createSpots(Garage garage) throws ServiceException {
        List<Spot> spots = spotService.spotsInGarage(garage);
        if (spots.size() == 0) {
            int len = GeneratorUtil.generateNumber();
            for (int i = 0; i < len; i++) {
                spotService.addSpot(new Spot(garage));
            }
        }
        return spotService.getSpots();
    }

    public int lengthAllSpots() {
        int len = 0;
        if (garageRepository.getAll().size() == 0) {
            return len;
        }
        for (Garage garage: garageRepository.getAll()) {
            len += garage.getSpots().size();
        }
        return len;
    }

    private List<Spot> spotsOnDate(LocalDateTime date, List<Order> orders) {
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
        if (garageRepository.getAll().size() == 0) {
            throw new ServiceException("Spots are not available");
        }
        for (Garage garage: garageRepository.getAll()) {
            List<Spot> busySpots = spotsOnDate(date, orders);

            for (Spot spot: garage.getSpots()) {
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
        if (orders.size() == 0) {
            return 0;
        }
        return (int) listAvailableSpots(futureDate, orders).stream().filter(Objects::nonNull).count();
    }

    @Override
    public void garagesFromCsv() throws ServiceException {

        List<List<String>> lists;

        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(garagePath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createGarages(lists);
            }
        } catch (CsvException e) {
            logger.warn("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createGarages(List<List<String>> lists) throws ServiceException {

        List<Garage> loadedGarages = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                int n = 0;
                int id = Integer.parseInt(list.get(n++));

                boolean exist = false;
                Garage newGarage = garageRepository.getById(id);
                if (newGarage != null) {
                    exist = true;
                } else {
                    newGarage = new Garage();
                }

                String name = list.get(n++);
                newGarage.setName(name);

                if (list.size() >= (n + 1)) {
                    List<String> idSpots = Arrays.asList(list.get(n++).split("\\|"));
                    spotsOfGarage(newGarage, idSpots);
                }
                if (list.size() >= (n + 1)) {
                    List<String> idMechanics = Arrays.asList(list.get(n).split("\\|"));
                    mechanicsOfGarage(newGarage, idMechanics);
                }
                if (exist) {
                    updateGarage(newGarage);
                } else {
                    loadedGarages.add(newGarage);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error with create garage from csv");
        }

        loadedGarages.forEach(System.out::println);
        garageRepository.addAll(loadedGarages);
    }

    private void mechanicsOfGarage(Garage newGarage, List<String> idMechanics) throws ServiceException {
        List<Mechanic> mechanics = new ArrayList<>();
        for (String idMechanicLine : idMechanics) {
            if (!idMechanicLine.isBlank()) {
                int idMechanic = Integer.parseInt(idMechanicLine);
                Mechanic mechanic = mechanicService.getMechanicById(idMechanic);
                if (mechanic != null) {
                    mechanics.add(mechanic);
                    mechanic.setGarage(newGarage);
                    mechanicService.updateMechanic(mechanic);
                }
            }
        }
        if (mechanics.size() > 0) {
            newGarage.setMechanics(mechanics);
        }
    }

    private void spotsOfGarage(Garage newGarage, List<String> idSpots) throws ServiceException {
        List<Spot> spots = new ArrayList<>();
        for (String idSpotLine : idSpots) {
            if (!idSpotLine.isBlank()) {
                int idSpot = Integer.parseInt(idSpotLine);
                Spot spot = spotService.getSpotById(idSpot);
                if (spot != null) {
                    spots.add(spot);
                    spot.setGarage(newGarage);
                    spotService.updateSpot(spot);
                }
            }
        }
        if (spots.size() > 0) {
            newGarage.setSpots(spots);
        }
    }

    public void garagesToCsv() {
        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(garagePath);
            for (Garage garage: garageRepository.getAll()) {
                if (garage != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(garage.getId()));
                    dataIn.add(garage.getName());

                    dataIn.add(spotsToCsv(garage));

                    dataIn.add(mechanicsToCsv(garage));

                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(file, headerCsv(), data);
        } catch (CsvException e) {
            logger.warn("Csv write exception " + e.getMessage());
        }
    }

    private String mechanicsToCsv(Garage garage) {
        StringBuilder mechanicsString = new StringBuilder();
        List<Mechanic> mechanics = mechanicService.getMechanics();
        for (Mechanic mechanic:mechanics) {
            if (mechanic != null && mechanic.getGarage().equals(garage)) {
                mechanicsString.append(mechanic.getId());
                mechanicsString.append("|");
            }
        }
        return mechanicsString.toString();
    }

    private String spotsToCsv(Garage garage) {
        StringBuilder spotsString = new StringBuilder();
        List<Spot> spots = spotService.getSpots();

        for (Spot spot: spots) {
            if (spot != null && spot.getGarage().equals(garage)) {
                spotsString.append(spot.getId());
                spotsString.append("|");
            }
        }
        return spotsString.toString();
    }

    private List<String> headerCsv() {
        List<String> header = new ArrayList<>();
        header.add(CsvGarageHeader.ID.getName());
        header.add(CsvGarageHeader.NAME.getName());
        header.add(CsvGarageHeader.SPOT_IDS.getName());
        header.add(CsvGarageHeader.MECHANIC_IDS.getName());
        return header;
    }
}
