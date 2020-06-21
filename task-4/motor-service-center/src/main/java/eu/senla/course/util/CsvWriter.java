package eu.senla.course.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    private static final String SEPARATOR = ";";

    public static void writeRecords(File file, List<String> header, List<List<String>> data) throws CsvException{
        try(FileWriter csvWriter = new FileWriter(file)){

            // TODO: Check more
            for (String head: header){
                csvWriter.append(head);
                csvWriter.append(SEPARATOR);
            }
            csvWriter.append("\n");
            for (List<String> row: data){
                csvWriter.append(String.join(SEPARATOR,row));
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            throw new CsvException("Error work with write to csv");
        }
    }
}
