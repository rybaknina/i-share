package eu.senla.course.service;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.annotation.di.Service;
import eu.senla.course.annotation.property.ConfigProperty;
import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.api.service.IToolService;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.CsvToolHeader;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.exception.CsvException;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ToolService implements IToolService {

    @ConfigProperty(key = "tool")
    private String toolPath;

    @Injection
    private IToolRepository toolRepository;

    public List<Tool> getTools() {
        return toolRepository.getAll();
    }

    public void setTools(List<Tool> tools) {
        toolRepository.setAll(tools);
    }

    public void addTool(Tool tool) throws ServiceException {
        try {
            toolRepository.add(tool);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public Tool getToolById(int id) {
        return toolRepository.getById(id);
    }

    public void deleteTool(Tool tool) {
        toolRepository.delete(tool);
    }

    public void updateTool(Tool tool) throws ServiceException {
        try {
            toolRepository.update(tool);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Override
    public void toolsFromCsv() throws ServiceException {
        List<List<String>> lists;
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(toolPath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createTools(lists);
            }
        } catch (CsvException e) {
            System.out.println("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createTools(List<List<String>> lists) throws ServiceException {

        List<Tool> loadedTools = new ArrayList<>();
        try {
            for (List<String> list : lists) {
                int n = 0;
                int id = Integer.parseInt(list.get(n++));
                String name = list.get(n++);
                int hours = Integer.parseInt(list.get(n++));
                BigDecimal hourlyPrice = new BigDecimal(list.get(n));

                Tool newTool = toolRepository.getById(id);
                if (newTool != null) {
                    newTool.setName(name);
                    newTool.setHours(hours);
                    newTool.setHourlyPrice(hourlyPrice);
                    updateTool(newTool);

                } else {
                    newTool = new Tool(name, hours, hourlyPrice);
                    loadedTools.add(newTool);
                }
            }
        } catch (Exception e){
            throw new ServiceException("Error with create tool from csv");
        }

        loadedTools.forEach(System.out::println);
        toolRepository.addAll(loadedTools);
    }

    @Override
    public void toolsToCsv() {

        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(toolPath);
            for (Tool tool: toolRepository.getAll()){
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
            System.out.println("Csv write exception " + e.getMessage());
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
