package eu.senla.course.service;

import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.api.service.IOrderService;
import eu.senla.course.api.service.IToolService;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.CsvToolHeader;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("toolService")
public class ToolService implements IToolService {
    private final static Logger logger = LogManager.getLogger(ToolService.class);
    @Value("${tool}")
    private String toolPath;


    private IToolRepository toolRepository;
    private IOrderService orderService;

    private Tool toolDtoToEntity(ToolDto toolDto) {
        Tool tool = new Tool();
        tool.setId(toolDto.getId());
        tool.setName(toolDto.getName());
        tool.setHourlyPrice(toolDto.getHourlyPrice());
        tool.setHours(toolDto.getHours());
        if (toolDto.getOrderDto() != null) {
            tool.setOrder(orderService.orderDtoToEntity(toolDto.getOrderDto()));
        }
        return tool;
    }

    @Autowired
    @Qualifier("toolHibernateRepository")
    public void setToolRepository(IToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Autowired
    @Qualifier("orderService")
    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional(readOnly = true)
    public List<ToolDto> getTools() {
        return toolRepository
                .getAll()
                .stream()
                .map(ToolDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void setTools(List<ToolDto> toolDtoList) {
        toolRepository.setAll(toolDtoList.stream().map(this::toolDtoToEntity).collect(Collectors.toList()));
    }

    @Transactional
    public void addTool(ToolDto toolDto) throws ServiceException {
        try {
            toolRepository.add(toolDtoToEntity(toolDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ToolDto getToolById(int id) {
        return new ToolDto(toolRepository.getById(id));
    }

    @Transactional
    public void deleteTool(int id) {
        toolRepository.delete(id);
    }

    @Transactional
    public void updateTool(ToolDto toolDto) throws ServiceException {
        try {
            toolRepository.update(toolDtoToEntity(toolDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void toolsFromCsv() throws ServiceException {
        List<List<String>> lists;
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(toolPath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createTools(lists);
            }
        } catch (CsvException e) {
            logger.warn("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createTools(List<List<String>> lists) throws ServiceException {

        List<ToolDto> loadedTools = new ArrayList<>();
        try {
            for (List<String> list : lists) {
                int n = 0;
                int id = Integer.parseInt(list.get(n++));
                String name = list.get(n++);
                int hours = Integer.parseInt(list.get(n++));
                BigDecimal hourlyPrice = new BigDecimal(list.get(n));

                Tool tool = toolRepository.getById(id);
                ToolDto newTool;
                if (tool != null) {
                    newTool = new ToolDto(tool);
                    updateTool(newTool);
                } else {
                    newTool = new ToolDto();
                    newTool.setName(name);
                    newTool.setHours(hours);
                    newTool.setHourlyPrice(hourlyPrice);
                    loadedTools.add(newTool);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error with create tool from csv");
        }

        loadedTools.forEach(System.out::println);
        toolRepository.addAll(loadedTools.stream().map(this::toolDtoToEntity).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public void toolsToCsv() {

        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(toolPath);
            List<ToolDto> toolDtoList = toolRepository.getAll().stream().map(ToolDto::new).collect(Collectors.toList());
            for (ToolDto tool: toolDtoList) {
                if (tool != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(tool.getId()));
                    dataIn.add(String.valueOf(tool.getName()));
                    dataIn.add(String.valueOf(tool.getHours()));
                    dataIn.add(String.valueOf(tool.getHourlyPrice()));
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
        header.add(CsvToolHeader.ID.getName());
        header.add(CsvToolHeader.NAME.getName());
        header.add(CsvToolHeader.HOURS.getName());
        header.add(CsvToolHeader.HOURLY_PRICE.getName());
        return header;
    }
}
