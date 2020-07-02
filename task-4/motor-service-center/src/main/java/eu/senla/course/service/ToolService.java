package eu.senla.course.service;

import eu.senla.course.api.IToolService;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.CsvToolHeader;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.repository.ToolRepository;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.PathToFile;
import eu.senla.course.util.exception.CsvException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToolService implements IToolService {

    private final static IToolService instance = new ToolService();
    private final static String TOOL_PATH = "tool";

    private List<Tool> tools;

    private ToolService() {
        this.tools = ToolRepository.getInstance().getAll();
    }

    public static IToolService getInstance(){
        return instance;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        ToolRepository.getInstance().setAll(tools);
    }

    public void addTool(Tool tool) throws ServiceException {
        try {
            ToolRepository.getInstance().add(tool);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public Tool getToolById(int id) throws ServiceException {
        Tool tool = ToolRepository.getInstance().getById(id);
        if (tool == null){
            throw new ServiceException("Tool is not found");
        }
        return tool;
    }

    public void deleteTool(Tool tool) throws ServiceException {
        try {
            ToolRepository.getInstance().delete(tool);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public void updateTool(Tool tool) throws ServiceException {
        try {
            ToolRepository.getInstance().update(tool);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    private Path getPath() throws ServiceException {
        return Optional.of(Paths.get(PathToFile.getPath(TOOL_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
    }

    @Override
    public void toolsFromCsv() throws ServiceException {
        List<List<String>> lists;
        Path path = this.getPath();
        try {
            lists = CsvReader.readRecords(Files.newBufferedReader(path));
            createTools(lists);

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

                Tool newTool = ToolRepository.getInstance().getById(id);
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
        ToolRepository.getInstance().addAll(loadedTools);
    }

    @Override
    public void toolsToCsv() throws ServiceException {

        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add(CsvToolHeader.ID.getName());
        headers.add(CsvToolHeader.NAME.getName());
        headers.add(CsvToolHeader.HOURS.getName());
        headers.add(CsvToolHeader.HOURLY_PRICE.getName());

        try {
            for (Tool tool: tools){
                if (tool != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(tool.getId()));
                    dataIn.add(String.valueOf(tool.getName()));
                    dataIn.add(String.valueOf(tool.getHours()));
                    dataIn.add(String.valueOf(tool.getHourlyPrice()));
                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(new File(String.valueOf(getPath())), headers, data);

        } catch (CsvException e) {
            System.out.println("Csv write exception" + e.getMessage());
        }
    }
}
