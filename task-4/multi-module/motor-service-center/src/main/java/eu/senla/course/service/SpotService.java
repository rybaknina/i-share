package eu.senla.course.service;

import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.api.service.IGarageService;
import eu.senla.course.api.service.ISpotService;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.dto.spot.SpotShortDto;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("spotService")
public class SpotService implements ISpotService {
    private final static Logger logger = LogManager.getLogger(SpotService.class);

    @Value("${spot}")
    private String spotPath;
    @Value("${modify.spot}")
    private boolean isModifySpot;

    private ISpotRepository spotRepository;
    private IGarageService garageService;


    public Spot spotDtoToEntity(SpotDto spotDto) {
        Spot spot = new Spot();
        spot.setId(spotDto.getId());
        if (spotDto.getGarageDto() != null) {
            Garage garage = garageService.garageDtoToEntity(spotDto.getGarageDto());
            spot.setGarage(garage);
        }
        return spot;
    }

    public Spot spotShortDtoToEntity(SpotShortDto spotShortDto) {
        Spot spot = new Spot();
        spot.setId(spotShortDto.getId());
        return spot;
    }
    @Autowired
    @Qualifier("spotHibernateRepository")
    public void setSpotRepository(ISpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Autowired
    @Qualifier("garageService")
    public void setGarageService(IGarageService garageService) {
        this.garageService = garageService;
    }

    @Transactional(readOnly = true)
    public List<SpotDto> getSpots() {
        return spotRepository
                .getAll()
                .stream()
                .map(SpotDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void setSpots(List<SpotDto> spotDtoList) {
        spotRepository.setAll(spotDtoList
                        .stream()
                        .map(this::spotDtoToEntity)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public void addSpot(SpotDto spotDto) throws ServiceException {
        try {
            spotRepository.add(spotDtoToEntity(spotDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Override
    public boolean isModifySpot() {
        return isModifySpot;
    }

    @Transactional(readOnly = true)
    public SpotDto getSpotById(int id) {
        return new SpotDto(spotRepository.getById(id));
    }

    @Transactional
    public void deleteSpot(int id) {
        spotRepository.delete(id);
    }

    @Transactional
    public void updateSpot(SpotDto spotDto) throws ServiceException {
        try {
            spotRepository.update(spotDtoToEntity(spotDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<SpotDto> spotsInGarage(GarageDto garageDto) {
        List<SpotDto> spotList = new ArrayList<>();
        if (garageDto != null) {
            List<SpotDto> spots = this.getSpots();
            for (SpotDto spot: spots) {
                if (spot.getGarageDto() != null && spot.getGarageDto().getId() == garageDto.getId()) {
                    spotList.add(spot);
                }
            }
        }
        return spotList;
    }

    @Transactional(readOnly = true)
    @Override
    public void spotsFromCsv() throws ServiceException {

        List<List<String>> lists;
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(spotPath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createFromCsvSpots(lists);
            }
        } catch (CsvException e) {
            logger.info("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createFromCsvSpots(List<List<String>> lists) throws ServiceException {
        List<SpotDto> loadedSpots = new ArrayList<>();
        try {
            for (List<String> list : lists) {
                int n = 0;
                int id = Integer.parseInt(list.get(n++));
                int garageId = Integer.parseInt(list.get(n));

                GarageDto garage = garageService.getGarageById(garageId);
                if (garage == null) {
                    throw new ServiceException("Garage is not found");
                }

                Spot spot = spotRepository.getById(id);
                SpotDto newSpot;
                if (spot != null) {
                    newSpot = new SpotDto(spot);
                    newSpot.setGarageDto(garage);
                    updateSpot(newSpot);
                } else {
                    newSpot = new SpotDto();
                    newSpot.setGarageDto(garage);
                    loadedSpots.add(newSpot);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error with create spot from csv");
        }

        loadedSpots.forEach(System.out::println);
        spotRepository.addAll(loadedSpots.stream().map(this::spotDtoToEntity).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
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
