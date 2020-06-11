package eu.senla.course.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class InputValidator {
    private static Pattern pattern = Pattern.compile("-?[0-9]+");
    private static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                                    .toFormatter();
    public static String readString(BufferedReader reader, String message) {
        System.out.println(message);
        return readString(reader);
    }
    public static String readString(BufferedReader reader) {
        String line = null;
        try {
            System.out.println("Enter text: ");
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println("Crush...");
        }
        return line;
    }

    public static Integer readInteger(BufferedReader reader, String message){
        System.out.println(message);
        return readInteger(reader);
    }
    public static Integer readInteger(BufferedReader reader){
        Integer number = null;
        boolean valid = false;
        while (!valid) {
            System.out.println("Enter number: ");
            try {
                String line = reader.readLine();
                if (line != null && !line.isEmpty() && pattern.matcher(line).matches()) {
                    number = Integer.parseInt(line);
                    valid = true;
                    continue;
                } else {
                    System.out.println("Not a Number entered! Try again...");
                    continue;
                }
            } catch (IOException e) {
                System.out.println("Crush...");
                valid = true;
            }
        }
        return number;
    }
    public static LocalDateTime readDateTime(BufferedReader reader, String message){
        System.out.println(message);
        return readDateTime(reader);
    }
    public static LocalDateTime readDateTime(BufferedReader reader){
        LocalDateTime dateTime = null;
        boolean valid = false;
        while (!valid) {
            System.out.println("Enter date-time in format \"dd.MM.yyyy HH:mm\" or \"yyyy-MM-dd HH:mm\": ");
            try {
                String line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    try {
                        dateTime = LocalDateTime.parse(line, formatter);
                    } catch (DateTimeParseException ex){
                        System.out.println("Wrong date-time entered! Try again...");
                        continue;
                    }
                    valid = true;
                    continue;
                } else {
                    System.out.println("Wrong date-time entered! Try again...");
                    continue;
                }
            } catch (IOException e) {
                System.out.println("Crush...");
                valid = true;
            }
        }
        return dateTime;
    }

    public static BigDecimal readDecimal(BufferedReader reader, String message){
        System.out.println(message);
        return readDecimal(reader);
    }

    public static BigDecimal readDecimal(BufferedReader reader){
        BigDecimal decimal = BigDecimal.ZERO;
        boolean valid = false;
        while (!valid) {
            System.out.println("Enter decimal: ");
            try {
                String line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    try {
                        decimal = new BigDecimal(line);
                    } catch (NumberFormatException ex){
                        System.out.println("Not a BigDecimal entered! Try again...");
                        continue;
                    }
                    valid = true;
                    continue;
                } else {
                    System.out.println("Not a BigDecimal entered! Try again...");
                    continue;
                }
            } catch (IOException e) {
                System.out.println("Crush...");
                valid = true;
            }
        }
        return decimal;
    }
}
