package eu.senla.course.service;

import eu.senla.course.api.IToolService;
import eu.senla.course.entity.Tool;
import eu.senla.course.util.CsvException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.PathToFile;

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
        this.tools = new ArrayList<>();
    }

    public static IToolService getInstance(){
        return instance;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public void addTool(Tool tool){
        tools.add(tool);
    }

    public Tool getToolById(int id){
        if (tools == null || tools.size() == 0){
            System.out.println("Tools are not exist");
            return null;
        }
        return tools.get(id);
    }

    public void deleteTool(Tool tool){
        if (tools == null || tools.size() == 0){
            System.out.println("Tools are not exist");
        } else {
            tools.removeIf(e -> e.equals(tool));
        }
    }

    public Tool getToolByName(String name){
        for (Tool tool: tools){
            if (tool.getName().equals(name)){
                return tool;
            }
        }
        System.out.println("Tool is not found");
        return null;
    }

    public void updateTool(int id, Tool tool) throws ServiceException {
        Optional.of(tools).orElseThrow(() -> new ServiceException("Orders are not found"));
        Optional.of(tools.set(id, tool)).orElseThrow(() -> new ServiceException("Order is not found"));;

    }

    private Path getPath() throws ServiceException {
        Path path = Optional.of(Paths.get(new PathToFile().getPath(TOOL_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
        return path;
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
        // TODO: need more tests
        List<Tool> loadedTools = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                String[] array = list.stream().toArray(String[]::new);
                int id = Integer.parseInt(array[0]) - 1;
                String name = array[1];
                int hours = Integer.parseInt(array[2]) - 1;
                BigDecimal hourlyPrice = new BigDecimal(array[3]);


                Tool newTool;
                if (tools.size() > 0 && Optional.of(tools.get(id)).isPresent()) {
                    newTool = tools.get(id);
                    newTool.setName(name);
                    newTool.setHours(hours);
                    newTool.setHourlyPrice(hourlyPrice);
                    updateTool(id, newTool);

                } else {
                    newTool = new Tool(name, hours, hourlyPrice);
                    loadedTools.add(newTool);
                }
            }
        } catch (Exception e){
            throw new ServiceException("Error with create tool from csv");
        }

        loadedTools.forEach(System.out::println);
        tools.addAll(loadedTools);
    }

    @Override
    public void toolsToCsv() throws ServiceException {
        // TODO: need more tests
        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add("id");
        headers.add("name");
        headers.add("hours");
        headers.add("hourly_price");

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
