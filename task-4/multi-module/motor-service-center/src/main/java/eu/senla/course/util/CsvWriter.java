package eu.senla.course.util;

import eu.senla.course.util.exception.CsvException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CsvWriter {
    private static final String SEPARATOR = ";";

    public static void writeRecords(File file, List<String> header, List<List<String>> data) throws CsvException {
        try (FileWriter csvWriter = new FileWriter(file)) {

            for (String head: header) {
                csvWriter.append(head);
                csvWriter.append(SEPARATOR);
            }
            csvWriter.append("\n");
            for (List<String> row: data) {
                csvWriter.append(String.join(SEPARATOR, row));
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            throw new CsvException("Error work with write to csv");
        }
    }

    public static File recordFile(String resource) {
        File file = new File(Objects.requireNonNull(CsvWriter.class.getClassLoader().getResource(resource)).getFile());
        return file;
    }
}