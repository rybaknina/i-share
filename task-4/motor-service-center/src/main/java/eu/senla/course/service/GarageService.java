package eu.senla.course.service;

import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.api.service.IGarageService;
import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.api.service.ISpotService;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.mechanic.MechanicShortDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.dto.spot.SpotShortDto;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("garageService")
public class GarageService implements IGarageService {
    private final static Logger logger = LogManager.getLogger(GarageService.class);
    @Value("${garage}")
    private String garagePath;

    private IGarageRepository garageRepository;
    private ISpotService spotService;
    private IMechanicService mechanicService;

    public GarageService() {
    }

    public Garage garageDtoToEntity(GarageDto garageDto) {
        Garage garage = new Garage();
        garage.setId(garageDto.getId());
        garage.setName(garageDto.getName());
        if (garageDto.getSpotShortDtoList() != null) {
            List<Spot> spots = new ArrayList<>();
            for (SpotShortDto spotShortDto: garageDto.getSpotShortDtoList()) {
                Spot spot = spotService.spotShortDtoToEntity(spotShortDto);
                if (spot != null) {
                    spot.setGarage(garage);
                    spots.add(spot);
                }
            }
            if (spots.size() != 0) {
                garage.setSpots(spots);
            }
        }
        if (garageDto.getMechanicShortDtoList() != null) {
            List<Mechanic> mechanics = new ArrayList<>();
            for (MechanicShortDto mechanicShortDto: garageDto.getMechanicShortDtoList()) {
                Mechanic mechanic = mechanicService.mechanicShortDtoToEntity(mechanicShortDto);
                mechanic.setGarage(garage);
                mechanics.add(mechanic);
            }
            if (mechanics.size() != 0) {
                garage.setMechanics(mechanics);
            }
        }
        return garage;
    }

    @Autowired
    @Qualifier("garageHibernateRepository")
    public void setGarageRepository(IGarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Autowired
    @Qualifier("spotService")
    public void setSpotService(ISpotService spotService) {
        this.spotService = spotService;
    }

    @Autowired
    @Qualifier("mechanicService")
    public void setMechanicService(IMechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @Transactional(readOnly = true)
    public List<GarageDto> getGarages() {
        return garageRepository
               .getAll()
               .stream()
               .map(GarageDto::new)
               .collect(Collectors.toList());
    }

    @Transactional
    public void setGarages(List<GarageDto> garageDtoList) {
        garageRepository.setAll(garageDtoList
                .stream()
                .map(this::garageDtoToEntity)
                .collect(Collectors.toList()));
    }

    @Transactional
    public void addGarage(GarageDto garageDto) throws ServiceException {
        try {
            garageRepository.add(garageDtoToEntity(garageDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional
    public void updateGarage(GarageDto garageDto) throws ServiceException {
        try {
            garageRepository.update(garageDtoToEntity(garageDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional
    public void deleteGarage(int id) {
        garageRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public GarageDto getGarageById(int id) {
        return new GarageDto(garageRepository.getById(id));
    }

    private List<SpotShortDto> createSpots(GarageDto garageDto) throws ServiceException {
        List<SpotDto> spots = spotService.spotsInGarage(garageDto);
        List <SpotShortDto> shortSpotListForGarage = new ArrayList<>();
        if (spots.size() == 0) {
            int len = GeneratorUtil.generateNumber();
            for (int i = 0; i < len; i++) {
                Spot spot = new Spot();
                SpotDto spotDto = new SpotDto(spot);
                SpotShortDto spotShortDto = new SpotShortDto(spot);
                shortSpotListForGarage.add(spotShortDto);
                spotService.addSpot(spotDto);
            }
        }
        return shortSpotListForGarage;
    }

    @Transactional(readOnly = true)
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

    private List<Spot> spotsOnDate(LocalDateTime date, List<OrderDto> orderDtoList) {
        List<Spot> spots = new ArrayList<>();
        if (orderDtoList != null && orderDtoList.size() != 0) {
            for (OrderDto order : orderDtoList) {
                if (order != null && order.getSpotShortDto() != null && (order.getCompleteDate() != null &&
                        (order.getCompleteDate().isAfter(date) && order.getStatus() != OrderStatus.CLOSE) ||
                        order.getStatus() == OrderStatus.IN_PROGRESS)) {
                    spots.add(spotService.spotShortDtoToEntity(order.getSpotShortDto()));
                }
            }
        }
        return spots;
    }

    @Transactional(readOnly = true)
    public List<SpotDto> listAvailableSpots(LocalDateTime date, List<OrderDto> orderDtoList) throws ServiceException {
        List<SpotDto> freeSpots = new ArrayList<>();
        if (garageRepository.getAll().size() == 0) {
            throw new ServiceException("Spots are not available");
        }
        for (Garage garage: garageRepository.getAll()) {
            List<Spot> busySpots = spotsOnDate(date, orderDtoList);

            for (Spot spot: garage.getSpots()) {
                if (spot != null) {
                    if (busySpots.size() == 0 || !busySpots.contains(spot)) {
                        freeSpots.add(new SpotDto(spot));
                    }
                }
            }
        }
        return freeSpots;
    }

    @Transactional(readOnly = true)
    public int numberAvailableSpots(LocalDateTime futureDate, List<OrderDto> orderDtoList) throws ServiceException {
        if (orderDtoList.size() == 0) {
            return 0;
        }
        return (int) listAvailableSpots(futureDate, orderDtoList).stream().filter(Objects::nonNull).count();
    }

    @Override
    @Transactional
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

        List<GarageDto> loadedGarages = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                int n = 0;
                int id = Integer.parseInt(list.get(n++));

                boolean exist = false;
                Garage garageById = garageRepository.getById(id);
                GarageDto newGarage;
                if (garageById != null) {
                    newGarage = new GarageDto(garageById);
                    exist = true;
                } else {
                    newGarage = new GarageDto();
                }

                String name = list.get(n++);
                newGarage.setName(name);

                if (list.size() >= (n + 1)) {
                    List<String> idSpots = Arrays.asList(list.get(n++).split("\\|"));
                    this.spotsOfGarage(newGarage, idSpots);
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
        garageRepository.addAll(loadedGarages.stream().map(this::garageDtoToEntity).collect(Collectors.toList()));
    }


    private void spotsOfGarage(GarageDto newGarage, List<String> idSpots) throws ServiceException {
        List<SpotShortDto> spots = new ArrayList<>();
        for (String idSpotLine : idSpots) {
            if (!idSpotLine.isBlank()) {
                int idSpot = Integer.parseInt(idSpotLine);
                SpotDto spot = spotService.getSpotById(idSpot);
                if (spot != null) {
                    spots.add(new SpotShortDto(spot));
                    spot.setGarageDto(newGarage);
                    spotService.updateSpot(spot);
                }
            }
        }
        if (spots.size() > 0) {
            newGarage.setSpotShortDtoList(spots);
        }
    }

    private void mechanicsOfGarage(GarageDto newGarage, List<String> idMechanics) throws ServiceException {
        List<MechanicShortDto> mechanics = new ArrayList<>();
        for (String idMechanicLine : idMechanics) {
            if (!idMechanicLine.isBlank()) {
                int idMechanic = Integer.parseInt(idMechanicLine);
                MechanicDto mechanic = mechanicService.getMechanicById(idMechanic);
                if (mechanic != null) {
                    mechanics.add(new MechanicShortDto(mechanic));
                    mechanic.setGarageShortDto(newGarage);
                    mechanicService.updateMechanic(mechanic);
                }
            }
        }
        if (mechanics.size() > 0) {
            newGarage.setMechanicShortDtoList(mechanics);
        }
    }

    @Transactional(readOnly = true)
    public void garagesToCsv() {
        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(garagePath);
            for (GarageDto garage: garageRepository.getAll().stream().map(GarageDto::new).collect(Collectors.toList())) {
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

    private String mechanicsToCsv(GarageDto garageDto) {
        StringBuilder mechanicsString = new StringBuilder();
        List<MechanicDto> mechanics = mechanicService.getMechanics();
        for (MechanicDto mechanic: mechanics) {
            if (mechanic != null && mechanic.getGarageShortDto().getId() == garageDto.getId()) {
                mechanicsString.append(mechanic.getId());
                mechanicsString.append("|");
            }
        }
        return mechanicsString.toString();
    }

    private String spotsToCsv(GarageDto garageDto) {
        StringBuilder spotsString = new StringBuilder();
        List<SpotDto> spots = spotService.getSpots();

        for (SpotDto spot: spots) {
            if (spot != null && spot.getGarageDto().getId() == garageDto.getId()) {
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
