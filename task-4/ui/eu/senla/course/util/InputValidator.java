package eu.senla.course.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Pattern;

public class InputValidator {
    private static Pattern pattern = Pattern.compile("-?[0-9]+");
    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            .toFormatter().localizedBy(Locale.ENGLISH);
    private static DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .toFormatter().localizedBy(Locale.ENGLISH);

    public static String readString(BufferedReader reader, String message) throws IOException {
        System.out.println(message);
        return readString(reader);
    }
    private static String readString(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    public static Integer readInteger(BufferedReader reader, String message) throws IOException {
        System.out.println(message);
        return readInteger(reader);
    }
    public static Integer readInteger(BufferedReader reader) throws IOException {
        Integer number = null;
        String line = reader.readLine();
        boolean valid = false;
        while (!valid) {
            if (pattern.matcher(line).matches()){
                number = Integer.parseInt(line);
                valid = true;
            } else {
                System.out.println("Not a Number entered! Try again...");
                line = reader.readLine();
                continue;
            }
        }
        return number;
    }

    public static LocalDate readDate(BufferedReader reader, String message) throws IOException {
        System.out.println(message);
        return readDate(reader);
    }

    private static LocalDate readDate(BufferedReader reader) throws IOException {
        LocalDate date = null;
        boolean valid = false;
        String line = reader.readLine();
        while (!valid) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(line, dateFormatter);
                valid = true;
            } catch (DateTimeParseException ex){
                System.out.println("Wrong date entered! Try again...");
                line = reader.readLine();
                continue;
            }
        }
        return date;
    }

    public static LocalDateTime readDateTime(BufferedReader reader, String message) throws IOException {
        System.out.println(message);
        return readDateTime(reader);
    }

    private static LocalDateTime readDateTime(BufferedReader reader) throws IOException {
        LocalDateTime dateTime = null;
        boolean valid = false;
        String line = reader.readLine();
        while (!valid) {
            try {
                //TODO: Check input. Something wrong. Need more debug - formatter?
                dateTime = LocalDateTime.parse(line, dateTimeFormatter);
                valid = true;
            } catch (DateTimeParseException ex){
                System.out.println("Wrong date-time entered! Try again...");
                line = reader.readLine();
                continue;
            }

        }
        return dateTime;
    }

    public static BigDecimal readDecimal(BufferedReader reader, String message) throws IOException {
        System.out.println(message);
        return readDecimal(reader);
    }

    private static BigDecimal readDecimal(BufferedReader reader) throws IOException {
        BigDecimal decimal = BigDecimal.ZERO;
        boolean valid = false;
        String line = reader.readLine();
        while (!valid) {
            try {
                decimal = new BigDecimal(line);
                valid = true;
            } catch (Exception e){
                System.out.println("Not a BigDecimal entered! Try again...");
                line = reader.readLine();
                continue;
            }
        }
        return decimal;
    }
}
