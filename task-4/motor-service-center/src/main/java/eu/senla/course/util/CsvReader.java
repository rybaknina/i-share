package eu.senla.course.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {
    private static final String SEPARATOR = ";";

    public static List<String> readHeader(Reader source) throws CsvException {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                    .findFirst()
                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
                    .get();
        } catch (IOException e) {
            throw new CsvException("Error work with headers CSV");
        }
    }
    public static List<List<String>> readRecords(Reader source) throws CsvException {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                    .skip(1)
                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CsvException("Error work with read records from CSV");
        }
    }
}
